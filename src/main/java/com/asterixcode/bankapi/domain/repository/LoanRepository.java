package com.asterixcode.bankapi.domain.repository;

import com.asterixcode.bankapi.domain.model.Loan;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends ListCrudRepository<Loan, Long> {
  List<Loan> findByCustomerIdOrderByStartDateDesc(long customerId);
}
