package com.asterixcode.eazybank.bankapi.infrastructure.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

public class RequestValidationBeforeFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
    if
    // Authorization header is required
    (Objects.isNull(authorizationHeader)) {
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      res.getWriter().write("Authorization header is required");
      return;
    } else if
    // Basic authentication is required
    (!authorizationHeader.startsWith("Basic")) {
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      res.getWriter().write("Basic authentication is required");
      return;
    } else if (StringUtils.startsWithIgnoreCase(authorizationHeader, "Basic")) {
      byte[] base64Token =
          authorizationHeader.substring("Basic".length()).trim().getBytes(StandardCharsets.UTF_8);
      byte[] decoded;
      try {
        decoded = java.util.Base64.getDecoder().decode(base64Token);
        String token = new String(decoded, StandardCharsets.UTF_8);
        int delimiter = token.indexOf(":");
        if (delimiter == -1) {
          throw new BadCredentialsException("Invalid basic authentication token");
        }
        String email = token.substring(0, delimiter);
        if (email.toLowerCase().contains("test")) {
          throw new BadCredentialsException("Test users are not allowed");
        }
      } catch (IllegalArgumentException e) {
        throw new BadCredentialsException("Failed to decode basic authentication token");
      }
    }
    chain.doFilter(request, response);
  }
}
