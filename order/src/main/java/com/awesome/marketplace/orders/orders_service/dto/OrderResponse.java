package com.awesome.marketplace.orders.orders_service.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
  public int id;
  public String status;
  public int quantity;
  public ProductResponse product;
  public Integer productId;
  public Date created_at;
  public Date updated_at;
}
