package com.asterixcode.bankapi.core.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BankUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  public BankUsernamePasswordAuthenticationProvider(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    // load user using BankUserDetailsService, which is our custom UserDetailsService implementation
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    // compare the password hashes (input password and the one stored in the database)
    if (passwordEncoder.matches(password, userDetails.getPassword())) {
      return new UsernamePasswordAuthenticationToken(
          username, password, userDetails.getAuthorities());
    } else {
      throw new BadCredentialsException("Invalid password");
    }
    // other validations can be done here such as fx: check user.age>18 if this is a requirement
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
