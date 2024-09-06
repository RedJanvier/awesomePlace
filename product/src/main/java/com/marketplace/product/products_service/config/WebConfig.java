package com.marketplace.product.products_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.marketplace.product.products_service.utils.HeaderValidationInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private HeaderValidationInterceptor headerValidationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerValidationInterceptor)
                .addPathPatterns("/api/**") // Add paths to which this interceptor should apply
                .excludePathPatterns("/api/review", "/api/order", "/api/auth");
    }
}
