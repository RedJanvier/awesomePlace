package com.awesome.marketplace.orders.orders_service.models;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order implements Comparable<Order>{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  public Integer id;
  
  @Column(nullable = false, name = "created_at")
  @CreationTimestamp
  public Date createdAt;
  
  @Column(nullable = false, name = "updated_at")
  @UpdateTimestamp
  public Date updatedAt;
  
  @Column(nullable = false)
  public String status = "pending";
  
  @Column(nullable = false)
  public int quantity;
  
  @Column(name = "product_id", nullable = false)
  public Integer productId;

  @Override
  public boolean equals(Object obj) {
    return ((Order) obj).getCreatedAt().equals(getCreatedAt());
  }

  @Override
  public int compareTo(Order order) {
    return getCreatedAt().compareTo(order.getCreatedAt());
  }
}
