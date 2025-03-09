package com.asterixcode.bankapi.application.controller;

import com.asterixcode.bankapi.application.assembler.CustomerDisassembler;
import com.asterixcode.bankapi.application.model.CustomerModel;
import com.asterixcode.bankapi.application.model.RegisterCustomerRequest;
import com.asterixcode.bankapi.application.openapi.CustomerOpenApi;
import com.asterixcode.bankapi.domain.model.Customer;
import com.asterixcode.bankapi.domain.service.CustomerRegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController implements CustomerOpenApi {

  private final CustomerDisassembler disassembler;
  private final CustomerRegistrationService service;

  public CustomerController(
      @Qualifier("customerDisassemblerImpl") CustomerDisassembler disassembler,
      CustomerRegistrationService service) {
    this.disassembler = disassembler;
    this.service = service;
  }

  @ResponseStatus(HttpStatus.CREATED)
  public CustomerModel register(@RequestBody @Valid RegisterCustomerRequest request) {
    Customer customer = service.register(disassembler.toDomainObject(request));
    return new CustomerModel(customer);
  }
}
