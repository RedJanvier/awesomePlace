package com.marketplace.product.products_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketplace.product.products_service.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {}
