package com.awesome.marketplace.orders.orders_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
  public int id;
  public String name;
  public Date created_at;
  public Date updated_at;
}
