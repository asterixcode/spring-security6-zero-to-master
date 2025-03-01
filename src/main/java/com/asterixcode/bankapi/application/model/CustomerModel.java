package com.asterixcode.bankapi.application.model;

import com.asterixcode.bankapi.domain.model.Customer;

public record CustomerModel(String email, String role) {
  public CustomerModel(Customer customer) {
    this(customer.getEmail(), customer.getRole());
  }
}
