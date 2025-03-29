package com.asterixcode.eazybank.bankapi.application.controller;

import com.asterixcode.eazybank.bankapi.domain.model.AccountTransactions;
import com.asterixcode.eazybank.bankapi.domain.repository.AccountTransactionsRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

  private final AccountTransactionsRepository accountTransactionsRepository;

  public BalanceController(AccountTransactionsRepository accountTransactionsRepository) {
    this.accountTransactionsRepository = accountTransactionsRepository;
  }

  @GetMapping("/myBalance")
  public List<AccountTransactions> getBalanceDetails(@RequestParam long id) {
    return accountTransactionsRepository.findByCustomerIdOrderByTransactionDateDesc(id);
  }
}
