package com.marketplace.product.products_service.controllers;

import com.marketplace.product.products_service.dto.CategoryRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;

import com.marketplace.product.products_service.services.CategoryService;
import com.marketplace.product.products_service.utils.AllowAdminOnly;
import com.marketplace.product.products_service.utils.ResponseHandler;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<Object> listCategories() {
    return ResponseHandler.generateResponse(categoryService.getAll(), HttpStatus.OK);
  }

  @PostMapping
  @AllowAdminOnly()
  public ResponseEntity<Object> createCategory(@RequestBody @Valid CategoryRequest category) {
    return ResponseHandler.generateResponse(categoryService.addOne(category), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  @AllowAdminOnly()
  public ResponseEntity<Object> delete(@PathVariable int id) {
    return ResponseHandler.generateResponse(categoryService.delete(id), HttpStatus.OK);
  }
  
}
