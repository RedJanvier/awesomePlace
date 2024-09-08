package com.marketplace.product.products_service.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  public CategoryResponseShort category;
  public Date created_at;
  public Date updated_at;
}
