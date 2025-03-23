package com.asterixcode.bankapi.infrastructure.security.filter;

import jakarta.servlet.*;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthoritiesLoggingAtFilter implements Filter {

  private static final Logger log = LoggerFactory.getLogger(AuthoritiesLoggingAtFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.info("Authentication validation is in progress...");
    chain.doFilter(request, response);
  }
}
