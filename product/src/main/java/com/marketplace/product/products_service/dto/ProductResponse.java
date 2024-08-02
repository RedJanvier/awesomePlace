package com.marketplace.product.products_service.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.marketplace.product.products_service.models.Category;

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
  public Category category;
  public Date created_at;
  public Date updated_at;
}
