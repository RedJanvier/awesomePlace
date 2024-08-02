package com.awesome.marketplace.orders.orders_service.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
class RestService<T> {
    private RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<T> sendRequest(
            String url,
            HttpMethod httpMethod,
            Object dtoObject,
            HttpHeaders headers,
            Class<T> classOfResponse
    ) throws Exception {
        try {
            return restTemplate.exchange(
                    url,
                    httpMethod,
                    new HttpEntity(dtoObject, headers),
                    classOfResponse
            );
        } catch (HttpStatusCodeException e) {
            // Deserialize error response
            throw new Exception(e);
        }
    }
}