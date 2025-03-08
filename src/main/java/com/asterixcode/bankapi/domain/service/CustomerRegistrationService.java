package com.asterixcode.bankapi.domain.service;

import com.asterixcode.bankapi.domain.model.Customer;
import com.asterixcode.bankapi.domain.repository.CustomerRepository;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerRegistrationService {

  private final CustomerRepository repository;
  private final PasswordEncoder passwordEncoder;

  public CustomerRegistrationService(
      CustomerRepository repository, PasswordEncoder passwordEncoder) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
  }

  public Customer register(Customer customer) {
    Optional<Customer> exists = repository.findByEmail(customer.getEmail());

    if (exists.isPresent() && !exists.get().equals(customer)) {
      throw new IllegalArgumentException(
          String.format("Customer with email %s already exists", customer.getEmail()));
    }

    if (customer.isNew()) {
      customer.setPassword(passwordEncoder.encode(customer.getPassword()));
    }

    return repository.save(customer);
  }

  public Customer getCustomerByEmail(String email) {
    return repository.findByEmail(email).orElse(null);
  }
}
