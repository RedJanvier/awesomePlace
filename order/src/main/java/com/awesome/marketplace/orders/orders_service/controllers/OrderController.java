package com.awesome.marketplace.orders.orders_service.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.awesome.marketplace.orders.orders_service.dto.OrderRequest;
import com.awesome.marketplace.orders.orders_service.services.OrderService;
import com.awesome.marketplace.orders.orders_service.utils.ResponseHandler;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
  
  @Autowired
  OrderService service;
  
  @GetMapping()
  public ResponseEntity<Object> listAll() {
      return ResponseHandler.generateResponse(service.getAll(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody OrderRequest review) throws Exception {
    return ResponseHandler.generateResponse(service.add(review), HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    return ResponseHandler.generateResponse(service.delete(id), HttpStatus.OK);
  }
}
