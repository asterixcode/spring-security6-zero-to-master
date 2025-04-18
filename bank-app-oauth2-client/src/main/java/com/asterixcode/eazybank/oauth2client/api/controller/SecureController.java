package com.asterixcode.eazybank.oauth2client.api.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecureController {

  @GetMapping("/secure")
  public String securePage(Authentication authentication) {
    if (authentication
        instanceof UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
      System.out.println("Basic Auth: " + usernamePasswordAuthenticationToken);
    } else if (authentication instanceof OAuth2AuthenticationToken oauth2AuthenticationToken) {
      System.out.println("OAuth2 Auth: " + oauth2AuthenticationToken);
    }
    return "secure.html";
  }
}
