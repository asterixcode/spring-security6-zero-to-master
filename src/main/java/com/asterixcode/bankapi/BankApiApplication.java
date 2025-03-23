package com.asterixcode.bankapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class BankApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankApiApplication.class, args);
  }
}
