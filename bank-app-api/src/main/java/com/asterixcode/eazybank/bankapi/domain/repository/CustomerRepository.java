package com.asterixcode.eazybank.bankapi.domain.repository;

import com.asterixcode.eazybank.bankapi.domain.model.Customer;
import java.util.Optional;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ListCrudRepository<Customer, Long> {

  Optional<Customer> findByEmail(String email);
}
