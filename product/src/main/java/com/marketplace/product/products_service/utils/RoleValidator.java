package com.marketplace.product.products_service.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class RoleValidator implements ConstraintValidator<AllowAdminOnly, Object> {
  private final String headerName = "X-auth-role";
  private String expectedValue;

  @Override
  public void initialize(AllowAdminOnly validHeader) {
    this.expectedValue = validHeader.role();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String headerValue = request.getHeader(headerName);
    log.info("The role sent: " + headerValue);
    return expectedValue.equals(headerValue);
  }
}
