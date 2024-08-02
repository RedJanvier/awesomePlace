package com.awesome.marketplace.orders.orders_service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.awesome.marketplace.orders.orders_service.clients.ProductClient;
import com.awesome.marketplace.orders.orders_service.dto.ProductResponse;
import com.awesome.marketplace.orders.orders_service.dto.ProductsClientResponseDto;
import com.awesome.marketplace.orders.orders_service.exceptions.CustomClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import com.awesome.marketplace.orders.orders_service.dto.OrderRequest;
import com.awesome.marketplace.orders.orders_service.dto.OrderResponse;
import com.awesome.marketplace.orders.orders_service.models.Order;
import com.awesome.marketplace.orders.orders_service.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
  private final OrderRepository repository;
  private final ProductClient productClient;
  private final RestService<ProductsClientResponseDto> restService;

  public List<OrderResponse> getAll() {
    return repository.findAllByOrderByCreatedAtDesc().stream().map(this::mapToDTO).toList();
  }

  public OrderResponse add(OrderRequest orderReq) throws CustomClientException {
    Map<String, Object> body = new HashMap<>();
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
    ResponseEntity<ProductsClientResponseDto> productResponse = null;
    try {
        productResponse = restService.sendRequest("http://localhost:8081/api/product/" + orderReq.productId, HttpMethod.GET, body, headers, ProductsClientResponseDto.class);
    } catch (Exception e) {
        throw new CustomClientException(HttpStatus.NOT_FOUND, "Product doesn't exist");
    }
    ProductResponse product = Objects.requireNonNull(productResponse.getBody()).getData();

    if (orderReq.quantity < product.getQuantity().intValueExact()) throw new CustomClientException(HttpStatus.BAD_REQUEST, "Product quantity requested is not available!");

    Order order = new Order();
    order.setStatus(orderReq.status);
    order.setProductId(orderReq.productId);
    order.setQuantity(orderReq.quantity);

    Order savedOrder = repository.save(order);

    // TODO: Send to KAFKA topic to reduce quantity of product

    return mapToDTO(savedOrder, product);
  }

  public OrderResponse findOne(int id) {
    return repository.findById(id).stream().map(this::mapToDTO).toList().get(0);
  }

  public String delete(int id) {
    repository.deleteById(id);
    return "Order deleted successfully";
  }

  private OrderResponse mapToDTO(Order review) {
    return OrderResponse.builder()
            .id(review.id)
            .status(review.status)
            .quantity(review.quantity)
            .productId(review.productId)
            .created_at(review.createdAt)
            .updated_at(review.updatedAt)
            .build();
  }

  // Override of OrderResponse to inject the product
  private OrderResponse mapToDTO(Order review, ProductResponse product) {
    return OrderResponse.builder()
            .id(review.id)
            .status(review.status)
            .product(product)
            .quantity(review.quantity)
            .productId(review.productId)
            .created_at(review.createdAt)
            .updated_at(review.updatedAt)
            .build();
  }
}
