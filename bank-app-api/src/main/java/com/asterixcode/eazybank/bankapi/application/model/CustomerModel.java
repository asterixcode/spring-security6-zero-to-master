package com.asterixcode.eazybank.bankapi.application.model;

import com.asterixcode.eazybank.bankapi.domain.model.Customer;

public record CustomerModel(String email, String role) {
  public CustomerModel(Customer customer) {
    this(customer.getEmail(), customer.getRole());
  }
}
