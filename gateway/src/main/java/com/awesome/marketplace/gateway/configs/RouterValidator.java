package com.awesome.marketplace.gateway.configs;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

@Service
public class RouterValidator {

    public static final List<String> openEndpoints = List.of(
        "/api/auth/register",
        "/api/auth/login",
        "/api/auth/verify",
        "/api/auth/refresh-token",
        "/api/auth/swagger",
        "/api/order/swagger",
        "/api/product/swagger"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}