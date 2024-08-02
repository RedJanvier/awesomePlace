package com.awesome.marketplace.orders.orders_service.clients;

import com.awesome.marketplace.orders.orders_service.dto.ProductsClientResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductClient {
    @GetExchange("/api/product/{id}")
    public ResponseEntity<ProductsClientResponseDto> findOneProduct(@PathVariable Integer id);
}
