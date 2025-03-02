package com.asterixcode.bankapi.core.security;

import com.asterixcode.bankapi.core.exception.CustomHttpBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Profile("local")
@Configuration
public class SecurityConfigurationLocal {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP traffic allowed
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers("/account", "/balance", "/loans", "/cards")
                    .authenticated()
                    .requestMatchers("/contact", "/notices", "/error")
                    .permitAll()
                    .requestMatchers("/api/v1/customers/register")
                    .permitAll()
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll());
    http.formLogin(Customizer.withDefaults());
    http.httpBasic(
        hbc -> hbc.authenticationEntryPoint(new CustomHttpBasicAuthenticationEntryPoint()));
    return http.build();
  }

  //  @Bean
  //  UserDetailsService userDetailsService(DataSource dataSource) {
  //    return new JdbcUserDetailsManager(dataSource);
  //  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public CompromisedPasswordChecker compromisedPasswordChecker() {
    return new HaveIBeenPwnedRestApiPasswordChecker();
  }
}
