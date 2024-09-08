package com.awesome.marketplace.orders.orders_service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.awesome.marketplace.orders.orders_service.clients.ProductClient;
import com.awesome.marketplace.orders.orders_service.dto.ProductResponse;
import com.awesome.marketplace.orders.orders_service.dto.ProductsClientResponseDto;
import com.awesome.marketplace.orders.orders_service.exceptions.CustomClientException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.awesome.marketplace.orders.orders_service.dto.OrderRequest;
import com.awesome.marketplace.orders.orders_service.dto.OrderResponse;
import com.awesome.marketplace.orders.orders_service.models.Order;
import com.awesome.marketplace.orders.orders_service.repositories.OrderRepository;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
  private final OrderRepository repository;
  private final RestService<ProductsClientResponseDto> restService;
  private Gson gson = new Gson();

  private static final String TOPIC = "products.reduce_quantity.evt";

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  public void sendKafkaMessage(String message) {
    kafkaTemplate.send(TOPIC, message);
  }

  public List<OrderResponse> getAll(String id, String role) {
    Integer userId = Integer.parseInt(id);
    List<OrderResponse> orders = role == "ADMIN"
      ? repository.findAll().stream().map(this::mapToDTO).toList()
      : repository.findAllByUserIdOrderByCreatedAtDesc(userId).stream().map(this::mapToDTO).toList();
    return orders;
  }

  public OrderResponse add(OrderRequest orderReq, String id) throws CustomClientException {
    Integer userId = Integer.parseInt(id);
    ResponseEntity<ProductsClientResponseDto> productResponse = null;

    try {
      Map<String, Object> body = new HashMap<>();
      HttpHeaders headers = new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
      productResponse = restService.sendRequest(
        "http://host.docker.internal:8081/api/product/" + orderReq.productId,
        HttpMethod.GET,
        body,
        headers,
        ProductsClientResponseDto.class
      );
    } catch (Exception e) {
      log.error("EXCEPTION CREATING ORDER: {}", e);
      throw new CustomClientException(HttpStatus.NOT_FOUND, "Product not found!");
    }
    
    ProductResponse product = Objects.requireNonNull(productResponse.getBody()).getData();

    if (orderReq.quantity <= 0)
      throw new CustomClientException(
        HttpStatus.BAD_REQUEST,
        "Product quantity must be greater than zero"
      );
    if (orderReq.quantity > product.getQuantity().intValueExact())
      throw new CustomClientException(
        HttpStatus.BAD_REQUEST,
        "Product quantity requested is not available! The only available quantity is: " + product.getQuantity().intValueExact()
      );

    Order order = new Order();
    order.setStatus(orderReq.status != null ? orderReq.status : "NEW");
    order.setProductId(orderReq.productId);
    order.setQuantity(orderReq.quantity);
    order.setUserId(userId);

    Order savedOrder = repository.save(order);

    sendKafkaMessage(gson.toJson(order));

    return mapToDTO(savedOrder, product);
  }

  public OrderResponse findOne(int id, String usrId) throws CustomClientException {
    Integer userId = Integer.parseInt(usrId);
    Order order = null;
    try {
      order = repository.findByIdAndUserId(id, userId).stream().toList().get(0);
    } catch (Exception e) {
      log.error("Issue retrieving the order {}", e);
    }
    if (order == null) throw new CustomClientException(HttpStatus.NOT_FOUND, "Order not found");
    return mapToDTO(order);
  }

  public OrderResponse updateStatus(Integer id, String status) throws CustomClientException {
    Optional<Order> res = repository.findById(id);

    if (res.isPresent()) {
      Order order = res.get();
      if (order.status.equalsIgnoreCase(status)) throw new CustomClientException(HttpStatus.BAD_REQUEST, "Order is already " + status, order);

      order.setStatus(status);

      // TODO: Send to KAFKA topic to email user for order status update

      return mapToDTO(repository.save(order));
    } else {
      throw new CustomClientException(HttpStatus.NOT_FOUND, "Product not found!");
    }
  }

  public String delete(int id) {
    repository.findById(id).orElseThrow(() -> new CustomClientException(HttpStatus.NOT_FOUND, "Order not found"));
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
