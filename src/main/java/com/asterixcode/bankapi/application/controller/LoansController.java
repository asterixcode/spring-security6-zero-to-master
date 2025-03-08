package com.asterixcode.bankapi.application.controller;

import com.asterixcode.bankapi.domain.model.Loan;
import com.asterixcode.bankapi.domain.repository.LoanRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

  private final LoanRepository loanRepository;

  public LoansController(LoanRepository loanRepository) {
    this.loanRepository = loanRepository;
  }

  @GetMapping("/myLoans")
  public List<Loan> getLoanDetails(@RequestParam long id) {
    return loanRepository.findByCustomerIdOrderByStartDateDesc(id);
  }
}
