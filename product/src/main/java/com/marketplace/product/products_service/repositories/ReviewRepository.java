package com.marketplace.product.products_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketplace.product.products_service.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {}
