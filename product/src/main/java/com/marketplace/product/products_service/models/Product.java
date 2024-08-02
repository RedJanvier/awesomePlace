package com.marketplace.product.products_service.models;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  public Integer id;
  
  @Column(nullable = false)
  @CreationTimestamp
  public Date created_at;
  
  @Column(nullable = false)
  @UpdateTimestamp
  public Date updated_at;
  
  @Column(nullable = false)
  public String name;
  
  @Column(nullable = false)
  public Double price;
  
  @Column
  public Boolean featured;
  
  @Column(nullable = false)
  public BigDecimal quantity;
  
  @Column
  public String[] tags;
  
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Category.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "category_id", nullable = false)
  public Category category;
}
