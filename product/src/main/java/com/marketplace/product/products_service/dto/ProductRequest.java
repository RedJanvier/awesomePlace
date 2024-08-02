package com.marketplace.product.products_service.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
  public String name;
  public Double price;
  public Boolean featured;
  public BigDecimal quantity;
  public String[] tags;
  public Integer category;
}
