package com.awesome.marketplace.orders.orders_service.config;

import com.awesome.marketplace.orders.orders_service.exceptions.CustomClientException;
import com.awesome.marketplace.orders.orders_service.utils.ResponseHandler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseHandler.generateResponse(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomClientException.class)
    public ResponseEntity<Object> handleAllExceptions(CustomClientException ex) {
        return ResponseHandler.generateResponse(null, ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Internal Server Error: " + ex.getMessage();
        log.error("INTERNAL SERVER ERROR: " + ex);

        return ResponseHandler.generateResponse(ex.getLocalizedMessage(), status, message);
    }
}