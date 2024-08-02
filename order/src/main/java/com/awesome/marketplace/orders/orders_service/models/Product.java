package com.awesome.marketplace.orders.orders_service.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale.Category;

public record Product(
    Integer id,
    String name,
    Double price,
    Boolean featured,
    BigDecimal quantity,
    String[] tags,
    Category category,
    Date created_at,
    Date updated_at
  ) {}
