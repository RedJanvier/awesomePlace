package com.awesome.marketplace.orders.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
  public Integer id;
  public String name;
  public Double price;
  public Boolean featured;
  public BigDecimal quantity;
  public String[] tags;
  public CategoryResponse category;
  public Date created_at;
  public Date updated_at;
}
