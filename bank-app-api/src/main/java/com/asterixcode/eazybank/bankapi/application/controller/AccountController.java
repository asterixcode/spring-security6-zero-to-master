package com.asterixcode.eazybank.bankapi.application.controller;

import com.asterixcode.eazybank.bankapi.domain.model.Account;
import com.asterixcode.eazybank.bankapi.domain.repository.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
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
