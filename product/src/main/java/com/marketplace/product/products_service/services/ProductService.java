package com.marketplace.product.products_service.services;

import com.google.gson.Gson;
import com.marketplace.product.products_service.dto.CategoryResponse;
import com.marketplace.product.products_service.dto.CategoryResponseShort;
import com.marketplace.product.products_service.dto.ProductRequest;
import com.marketplace.product.products_service.dto.ProductResponse;
import com.marketplace.product.products_service.exceptions.CustomClientException;
import com.marketplace.product.products_service.models.Category;
import com.marketplace.product.products_service.models.Product;
import com.marketplace.product.products_service.models.QProduct;
import com.marketplace.product.products_service.repositories.ProductRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryService categoryService;
  private final EntityManager entityManager;
  private final Gson gson = new Gson();

  public List<ProductResponse> getAll(Integer category, String tag, String query) {
    // List<Product> products = productRepository.findAll();

    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    QProduct product = QProduct.product;
    JPAQuery<Product> queryString = queryFactory.selectFrom(product);
    Boolean isNewQuery = true;
    BooleanExpression exp = null;
    if (query != null) {
      exp = product.name.containsIgnoreCase(query);
      isNewQuery = false;
    }
    
    if (tag != null) {
      exp = !isNewQuery ? exp.and(Expressions.booleanTemplate("function('ARRAY_CONTAINS', {0}, {1})", product.tags, tag))
        : Expressions.booleanTemplate("function('ARRAY_CONTAINS', {0}, {1})", product.tags, tag);
      isNewQuery = false;
    }

    if (category != null) {
      exp = !isNewQuery ? exp.and(product.category.id.eq(category)) : product.category.id.eq(category);
      isNewQuery = false;
    }

    if (query != null || tag != null || category != null) queryString = queryString.where(exp);
    List<Product> products = queryString.fetch();
    return products.stream().map(this::getProductResponse).toList();
  }

  public ProductResponse add(ProductRequest productReq) {
    Category category = categoryService.findOne(productReq.category);

    Product product = new Product();
    product.setCategory(category);
    product.setFeatured(productReq.featured);
    product.setName(productReq.name);
    product.setPrice(productReq.price);
    product.setQuantity(productReq.quantity);
    product.setTags(productReq.tags);

    Product created = productRepository.save(product);
    return getProductResponse(created);
  }

  public ProductResponse findOne(int id) throws Exception {
    Product found = findOneProduct(id);
    return getProductResponse(found);
  }

  public Product findOneProduct(int id) throws CustomClientException {
    return productRepository.findById(id).orElseThrow(() -> new CustomClientException(HttpStatus.NOT_FOUND, "Product not found"));
  }

  public String delete(int id) throws CustomClientException {
    findOneProduct(id);
    productRepository.deleteById(id);
    return "Product deleted successfully";
  }

  public ProductResponse setAsFeatured(int id) throws CustomClientException {
    Optional<Product> res = productRepository.findById(id);

    if (res.isPresent()) {
      Product product = res.get();
      if (product.featured) throw new CustomClientException(HttpStatus.BAD_REQUEST, "Product is already featured!", product);

      product.setFeatured(true);

      return getProductResponse(productRepository.save(product));
    } else {
      throw new CustomClientException(HttpStatus.NOT_FOUND, "Product not found!");
    }
  }

  @KafkaListener(topics = "products.reduce_quantity.evt", groupId = "products-consumer")
  public void reduceQty(String json) throws CustomClientException {
    HashMap<String, Object> order = gson.fromJson(json, HashMap.class);
    BigDecimal quantity = new BigDecimal(order.get("quantity").toString());
    Integer id = Integer.parseInt(order.get("productId").toString().split("\\.")[0]);

    Optional<Product> res = productRepository.findById(id);

    if (res.isPresent()) {
      Product product = res.get();
      product.setQuantity(product.quantity.subtract(quantity));
      productRepository.save(product);
    } else {
      throw new CustomClientException(HttpStatus.NOT_FOUND, "Product not found!");
    }
  }

  private ProductResponse getProductResponse(Product product) {
    CategoryResponseShort category = CategoryResponseShort.builder()
      .id(product.getCategory().id)
      .name(product.getCategory().name)
      .build();
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .tags(product.getTags())
            .featured(product.getFeatured())
            .category(category)
            .created_at(product.getCreated_at())
            .updated_at(product.getUpdated_at())
            .build();
  }
}