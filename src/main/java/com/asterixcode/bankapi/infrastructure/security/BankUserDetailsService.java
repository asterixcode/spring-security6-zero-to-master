package com.asterixcode.bankapi.infrastructure.security;

import com.asterixcode.bankapi.domain.model.Customer;
import com.asterixcode.bankapi.domain.repository.CustomerRepository;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BankUserDetailsService implements UserDetailsService {

  private final CustomerRepository customerRepository;

  public BankUserDetailsService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Customer customer =
        customerRepository
            .findByEmail(username)
            .orElseThrow(
                () -> new UsernameNotFoundException("User details not found for user " + username));
    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
    return new User(customer.getEmail(), customer.getPassword(), authorities);
  }
}
