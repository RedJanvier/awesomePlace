package com.awesome.marketplace.gateway.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrix
public class GatewayConfig {
    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/api/product/**", "/api/review/**", "/api/category/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://product-service:8081"))
                .route("order-service", r -> r.path("/api/order/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://order-service:8082"))
                .route("auth-service", r -> r.path("/api/auth/**", "/api/profile/**")
                        .filters(f -> f.filter(filter))
                        .uri("http://auth-service:4000"))
                .build();
    }
}
