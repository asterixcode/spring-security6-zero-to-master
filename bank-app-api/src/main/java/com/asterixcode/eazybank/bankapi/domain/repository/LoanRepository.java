package com.asterixcode.eazybank.bankapi.domain.repository;

import com.asterixcode.eazybank.bankapi.domain.model.Loan;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends ListCrudRepository<Loan, Long> {

  // @PreAuthorize("hasRole('USER')")
  List<Loan> findByCustomerIdOrderByStartDateDesc(long customerId);
}
