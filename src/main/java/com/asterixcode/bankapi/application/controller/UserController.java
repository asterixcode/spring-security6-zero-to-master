package com.asterixcode.bankapi.application.controller;

import com.asterixcode.bankapi.domain.model.Customer;
import com.asterixcode.bankapi.domain.service.CustomerRegistrationService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final CustomerRegistrationService service;

  public UserController(CustomerRegistrationService service) {
    this.service = service;
  }

  @GetMapping("/user")
  public Customer getUserDetailsAfterLogin(Authentication authentication) {
    return service.getCustomerByEmail(authentication.getName());
  }
}
