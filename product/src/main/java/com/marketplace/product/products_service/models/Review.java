package com.marketplace.product.products_service.models;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "reviews")
public class Review {
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
  public String message;
  
  @Column(name = "reviewer_id", nullable = false)
  public int reviewerId;
  
  @ManyToOne(fetch = FetchType.EAGER, targetEntity = Product.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "product_id", nullable = false)
  public Product product;
}
