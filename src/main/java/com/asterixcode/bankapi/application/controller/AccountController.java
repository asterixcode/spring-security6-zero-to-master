package com.asterixcode.bankapi.application.controller;

import com.asterixcode.bankapi.domain.model.Account;
import com.asterixcode.bankapi.domain.repository.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  private final AccountRepository accountsRepository;

  public AccountController(AccountRepository accountsRepository) {
    this.accountsRepository = accountsRepository;
  }

  @GetMapping("/myAccount")
  public Account getAccountDetails(@RequestParam long id) {
    return accountsRepository.findByCustomerId(id);
  }
}
