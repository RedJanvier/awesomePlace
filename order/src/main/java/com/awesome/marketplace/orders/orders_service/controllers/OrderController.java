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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
  
  @Autowired
  OrderService service;
  
  @GetMapping()
  public ResponseEntity<Object> listAll(@RequestHeader("X-auth-id") String userId, @RequestHeader("X-auth-role") String userRole) {
    return ResponseHandler.generateResponse(service.getAll(userId, userRole), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Object> create(@RequestBody OrderRequest order, @RequestHeader("X-auth-id") String userId) throws Exception {
    return ResponseHandler.generateResponse(service.add(order, userId), HttpStatus.CREATED);
  }

  @PutMapping("{id}/{status}")
  public ResponseEntity<Object> updateStatus(@PathVariable String id, @PathVariable String status) {      
    return ResponseHandler.generateResponse(service.updateStatus(Integer.parseInt(id), status.toUpperCase()), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Object> trackOrder(@PathVariable int id, @RequestHeader("X-auth-id") String userId) {
    return ResponseHandler.generateResponse(service.findOne(id, userId), HttpStatus.OK);
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable int id) {
    return ResponseHandler.generateResponse(service.delete(id), HttpStatus.OK);
  }
}
