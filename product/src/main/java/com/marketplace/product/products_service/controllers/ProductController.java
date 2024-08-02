package com.marketplace.product.products_service.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.marketplace.product.products_service.dto.ProductRequest;
import com.marketplace.product.products_service.services.ProductService;
import com.marketplace.product.products_service.utils.ResponseHandler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

  @Autowired
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<Object> listProducts(@RequestParam(required = false) Integer category, @RequestParam(required = false) String tag, @RequestParam(required = false) String query) {
      return ResponseHandler.generateResponse(productService.getAll(category, tag, query), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody ProductRequest product) {
    return ResponseHandler.generateResponse(productService.add(product), HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> getOne(@PathVariable int id) throws Exception {
    return ResponseHandler.generateResponse(productService.findOne(id), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> makeFeatured(@PathVariable int id) throws Exception {
    return ResponseHandler.generateResponse(productService.setAsFeatured(id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    return ResponseHandler.generateResponse(productService.delete(id), HttpStatus.OK);
  }
  
}
