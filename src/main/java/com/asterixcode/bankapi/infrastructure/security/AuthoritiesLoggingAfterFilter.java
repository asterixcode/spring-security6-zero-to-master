package com.asterixcode.bankapi.infrastructure.security;

import jakarta.servlet.*;
import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthoritiesLoggingAfterFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(AuthoritiesLoggingAfterFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (Objects.nonNull(authentication)) {
      log.info(
          "User {} is successfully authenticated and has the authorities: {}",
          authentication.getName(),
          authentication.getAuthorities().toString());
    }
    chain.doFilter(request, response);
  }
}
