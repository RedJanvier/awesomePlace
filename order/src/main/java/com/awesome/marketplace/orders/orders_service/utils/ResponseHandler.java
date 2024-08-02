package com.awesome.marketplace.orders.orders_service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status, String message) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status.value());
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status) {
        String message = "Success";
        if (status.value() >= 300 && status.value() < 400) message = "Redirected";
        else if (status.value() >= 400 && status.value() < 500) message = "Bad request";
        else if (status.value() >= 500) message = "Server Error";

        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }
    public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatusCode status) {
        String message = "Success";
        if (status.value() >= 300 && status.value() < 400) message = "Redirected";
        else if (status.value() >= 400 && status.value() < 500) message = "Bad request";
        else if (status.value() >= 500) message = "Server Error";

        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", message);
            map.put("status", status);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
            map.put("message", "Success");
            map.put("status", HttpStatus.OK);
            map.put("data", responseObj);

            return new ResponseEntity<Object>(map,HttpStatus.OK);
    }
}