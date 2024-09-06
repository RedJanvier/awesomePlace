package com.marketplace.product.products_service.utils;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HeaderValidationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
        jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
          String headerValue = request.getHeader("X-auth-role");
          List<String> adminMethods = List.of("PUT", "POST", "PATCH", "DELETE");
  
          if (adminMethods.contains(request.getMethod().toUpperCase()))
            if (!"ADMIN".equals(headerValue)) {
              response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Role");
              return false;
            }
          return true;
    }
}
