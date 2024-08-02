package com.marketplace.product.products_service.services;

import com.marketplace.product.products_service.dto.ProductRequest;
import com.marketplace.product.products_service.dto.ProductResponse;
import com.marketplace.product.products_service.exceptions.CustomClientException;
import com.marketplace.product.products_service.models.Category;
import com.marketplace.product.products_service.models.Product;
import com.marketplace.product.products_service.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryService categoryService;

  public List<ProductResponse> getAll(Integer category, String tag, String query) {
    List<Product> products = productRepository.findAll();
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

  public Product findOneProduct(int id) throws Exception {
    return productRepository.findById(id).orElseThrow(() -> new CustomClientException(HttpStatus.NOT_FOUND, "Product not found"));
  }

  public String delete(int id) {
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

  private ProductResponse getProductResponse(Product product) {
    return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .price(product.getPrice())
            .quantity(product.getQuantity())
            .tags(product.getTags())
            .featured(product.getFeatured())
            .category(product.getCategory())
            .created_at(product.getCreated_at())
            .updated_at(product.getUpdated_at())
            .build();
  }
}