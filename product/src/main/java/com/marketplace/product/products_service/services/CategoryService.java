package com.marketplace.product.products_service.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.marketplace.product.products_service.dto.CategoryRequest;
import com.marketplace.product.products_service.dto.CategoryResponse;
import com.marketplace.product.products_service.exceptions.CustomClientException;
import com.marketplace.product.products_service.models.Category;
import com.marketplace.product.products_service.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
  
  private final CategoryRepository categoryRepository;

  public List<CategoryResponse> getAll() {
    return categoryRepository.findAll().stream().map(this::mapToCategoryResponse).toList();
  }

  public CategoryResponse addOne(CategoryRequest categoryReq) {
    Category category = new Category();
    category.setName(categoryReq.name);
    return mapToCategoryResponse(categoryRepository.save(category));
  }
  
  public Category findOne(Integer id) {
    return categoryRepository.findById(id).orElseThrow(() -> new CustomClientException(HttpStatus.NOT_FOUND, "Category not found"));
  }

  public String delete(int id) {
    categoryRepository.deleteById(id);
    return "Category deleted successfully";
  }

  private CategoryResponse mapToCategoryResponse(Category category) {
    return CategoryResponse.builder()
      .id(category.id)
      .name(category.name)
      .created_at(category.created_at)
      .updated_at(category.updated_at)
      .build();
  }
}
