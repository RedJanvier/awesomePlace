package com.awesome.marketplace.orders.orders_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.awesome.marketplace.orders.orders_service.utils.HeaderValidationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private HeaderValidationInterceptor headerValidationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerValidationInterceptor)
                .addPathPatterns("/api/order/**"); // Add paths to which this interceptor should apply
    }
}
