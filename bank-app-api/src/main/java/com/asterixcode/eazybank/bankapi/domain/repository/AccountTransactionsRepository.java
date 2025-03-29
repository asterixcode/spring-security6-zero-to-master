package com.asterixcode.eazybank.bankapi.domain.repository;

import com.asterixcode.eazybank.bankapi.domain.model.AccountTransactions;
import java.util.List;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionsRepository
    extends ListCrudRepository<AccountTransactions, Long> {
  List<AccountTransactions> findByCustomerIdOrderByTransactionDateDesc(Long customerId);
}
