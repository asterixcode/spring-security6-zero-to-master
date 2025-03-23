package com.asterixcode.bankapi.infrastructure.security.filter;

import com.asterixcode.bankapi.infrastructure.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.crypto.SecretKey;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    // Read the Authorization header, where the JWT token should be
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // If the Authorization header with the JWT is not null, validate the JWT token
    if (Objects.nonNull(authorizationHeader)) {
      try {
        // Get the environment
        Environment env = getEnvironment();
        // Get the secret key from the environment
        String secret =
            env.getProperty(
                ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT);
        // Get the secret key
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        // Validate the JWT token
        Claims claims =
            Jwts.parser()
                // Validate the token
                .verifyWith(secretKey)
                // If the token is invalid or has been tampered, an exception will be thrown
                .build()
                // fetch the username and the authorities from the token
                .parseSignedClaims(authorizationHeader)
                // username and authorities are stored in the payload, which are the Claims
                // Claims is a Map<String, Object> object that stores the payload of the JWT token
                .getPayload();

        // Get the username from the payload as claims map
        String username = String.valueOf(claims.get("username"));
        // Get the authorities from the payload as claims map
        String authorities = String.valueOf(claims.get("authorities"));

        // Create an authentication object with the username and authorities
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(
                // username
                username,
                // password
                null,
                // authorities needs to be transformed from String to list of GrantedAuthority
                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
        /*
        On UsernamePasswordAuthenticationToken creation,
        it does super.setAuthenticated(true) by default in the constructor.
        This prevents the application to try to authenticate the user again.
        How it prevents? As this Filter JWTTokenValidatorFilter is before the BasicAuthenticationFilter,
        and the boolean isAuthenticated is now set to true,
        the BasicAuthenticationFilter will not try to authenticate the user again.
        */

        /*
        Once UsernamePasswordAuthenticationToken is created,
        we also need to store it in the SecurityContextHolder
        */
        SecurityContextHolder.getContext().setAuthentication(authentication);

      } catch (Exception e) {
        throw new BadCredentialsException("Invalid JWT token");
      }
    }

    // invoke the next filter in the chain
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    // Do not filter the /user endpoint, which is my login endpoint
    return request.getServletPath().equals("/user");
  }
}
