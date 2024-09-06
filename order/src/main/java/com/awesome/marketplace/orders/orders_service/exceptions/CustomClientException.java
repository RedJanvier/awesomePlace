package com.awesome.marketplace.orders.orders_service.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomClientException extends RuntimeException {
    private HttpStatus status;
    private String message;
    private Object data = null;

    public CustomClientException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
    
    public CustomClientException(HttpStatus status, String message, Object data) {
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @Override
    public String getMessage() {
        return message;
    }
}