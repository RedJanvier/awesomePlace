package com.marketplace.product.products_service.dto;

import java.util.Date;

import com.marketplace.product.products_service.models.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
  public int id;
  public String message;
  public int reviewerId;
  public Product product;
  public Date created_at;
  public Date updated_at;
}
