package com.asterixcode.eazybank.bankapi.infrastructure.security.filter;

import com.asterixcode.eazybank.bankapi.infrastructure.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    // Get the authentication object from the security context
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // If the authentication object is not null, generate the JWT token
    if (Objects.nonNull(authentication)) {
      // Get the environment
      Environment env = getEnvironment();
      // Get the secret key from the environment
      String secret =
          env.getProperty(
              // Get the JWT secret key from the environment
              ApplicationConstants.JWT_SECRET_KEY,
              // If the JWT secret key is not found, use the default secret key
              ApplicationConstants.JWT_SECRET_DEFAULT);
      // Get the secret key
      SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

      // Generate the JWT token
      String jwtToken =
          Jwts.builder()
              /* 1- Set the header */
              .header()
              .type("JWT")
              .and()
              /* 2- Set the payload */
              .issuer("The Bank API")
              .subject("JWT Token")
              // Add the username to the token
              .claim("username", authentication.getName())
              // Add the authorities to the token
              .claim(
                  "authorities",
                  authentication.getAuthorities().stream()
                      .map(GrantedAuthority::getAuthority)
                      .collect(Collectors.joining(",")))
              .issuedAt(new Date())
              .notBefore(new Date())
              .expiration(new Date(new Date().getTime() + 30000000)) // 8 hours
              /* 3- Sign the token with the secret key */
              .signWith(secretKey)
              // Compact the token = return the token as a string
              .compact();

      // Add the JWT token to the response header "Authorization"
      response.setHeader(HttpHeaders.AUTHORIZATION, jwtToken);
    }

    // Continue the filter chain
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    // Only filter the /user endpoint, which is the login endpoint
    return !request.getServletPath().equals("/user");
  }
}
