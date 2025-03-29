package com.asterixcode.eazybank.bankapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity
@ConfigurationPropertiesScan
public class BankApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankApiApplication.class, args);
  }
}
