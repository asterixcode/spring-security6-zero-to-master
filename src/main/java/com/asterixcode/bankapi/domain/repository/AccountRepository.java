package com.asterixcode.bankapi.domain.repository;

import com.asterixcode.bankapi.domain.model.Account;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends ListCrudRepository<Account, Long> {

  Account findByCustomerId(long customerId);
}
