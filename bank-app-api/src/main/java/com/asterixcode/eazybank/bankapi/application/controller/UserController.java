package com.asterixcode.eazybank.bankapi.application.controller;

import com.asterixcode.eazybank.bankapi.application.model.LoginRequest;
import com.asterixcode.eazybank.bankapi.application.model.LoginResponse;
import com.asterixcode.eazybank.bankapi.domain.model.Customer;
import com.asterixcode.eazybank.bankapi.domain.service.CustomerRegistrationService;
import com.asterixcode.eazybank.bankapi.infrastructure.constants.ApplicationConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final CustomerRegistrationService service;
  private final AuthenticationManager authenticationManager;
  private final Environment env;

  public UserController(
      CustomerRegistrationService service,
      AuthenticationManager authenticationManager,
      Environment env) {
    this.service = service;
    this.authenticationManager = authenticationManager;
    this.env = env;
  }

  @GetMapping("/user")
  public Customer getUserDetailsAfterLogin(Authentication authentication) {
    return service.getCustomerByEmail(authentication.getName());
  }

  @PostMapping("/apiLogin")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    String jwtToken = "";
    Authentication authentication =
        UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password());
    /* Once the Authentication is created, the authentication operation needs to be invoked manually.
    The AuthenticationManager @Bean create inside SecurityConfiguration is used for that process */
    Authentication authenticationResponse = authenticationManager.authenticate(authentication);
    if (Objects.nonNull(authenticationResponse) && authenticationResponse.isAuthenticated()) {
      String secret =
          env.getProperty(
              ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT);
      SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

      jwtToken =
          Jwts.builder()
              .header()
              .type("JWT")
              .and()
              .issuer("The Bank API")
              .subject("JWT Token")
              .claim("username", authenticationResponse.getName())
              .claim(
                  "authorities",
                  authenticationResponse.getAuthorities().stream()
                      .map(GrantedAuthority::getAuthority)
                      .collect(Collectors.joining(",")))
              .issuedAt(new Date())
              .notBefore(new Date())
              .expiration(new Date(new Date().getTime() + 30000000))
              .signWith(secretKey)
              .compact();
    }
    return ResponseEntity.status(HttpStatus.OK)
        .header(HttpHeaders.AUTHORIZATION, jwtToken)
        .body(new LoginResponse(HttpStatus.OK.getReasonPhrase(), jwtToken));
  }
}
