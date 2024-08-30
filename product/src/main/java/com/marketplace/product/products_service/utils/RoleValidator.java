// package com.marketplace.product.products_service.utils;

// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;

// import javax.servlet.http.HttpServletRequest;
// import javax.validation.ConstraintValidator;
// import javax.validation.ConstraintValidatorContext;

// public class RoleValidator implements ConstraintValidator<RoleValidatorBody, Object> {
//   private final String headerName = "X-auth-role";
//   private String expectedValue;

//   @Override
//   public void initialize(RoleValidatorBody validHeader) {
//     this.expectedValue = validHeader.role();
//   }

//   @Override
//   public boolean isValid(Object value, ConstraintValidatorContext context) {
//     HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//     String headerValue = request.getHeader(headerName);
//     return expectedValue.equals(headerValue);
//   }
// }
