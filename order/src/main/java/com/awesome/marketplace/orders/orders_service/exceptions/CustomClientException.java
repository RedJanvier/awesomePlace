package com.awesome.marketplace.orders.orders_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomClientException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public CustomClientException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}