package com.asterixcode.eazybank.bankapi.application.controller;

import com.asterixcode.eazybank.bankapi.domain.model.Loan;
import com.asterixcode.eazybank.bankapi.domain.repository.LoanRepository;
import java.util.List;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

  private final LoanRepository loanRepository;

  public LoansController(LoanRepository loanRepository) {
    this.loanRepository = loanRepository;
  }

  @GetMapping("/myLoans")
  @PostAuthorize("hasRole('USER')")
  public List<Loan> getLoanDetails(@RequestParam long id) {
    return loanRepository.findByCustomerIdOrderByStartDateDesc(id);
  }
}
