package com.awesome.marketplace.orders.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsClientResponseDto {
    public ProductResponse data;
    public String message;
    public String status;
}
