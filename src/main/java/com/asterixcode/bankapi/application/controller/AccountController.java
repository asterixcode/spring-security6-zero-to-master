package com.asterixcode.bankapi.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

  @GetMapping
  public String getAccountDetails() {
    return "Account Details";
  }
}
