package com.asterixcode.bankapi.infrastructure.security;

import com.asterixcode.bankapi.infrastructure.exception.CustomAccessDeniedHandler;
import com.asterixcode.bankapi.infrastructure.exception.CustomHttpBasicAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Profile("!local")
@Configuration
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.cors(
            corsConfig ->
                corsConfig.configurationSource(
                    request -> {
                      CorsConfiguration config = new CorsConfiguration();
                      config.setAllowedOrigins(
                          Collections.singletonList("http://localhost:4200"));
                      config.setAllowedMethods(Collections.singletonList("*"));
                      config.setAllowedHeaders(Collections.singletonList("*"));
                      config.setAllowCredentials(true);
                      config.setMaxAge(3600L);
                      return config;
                    }))
        .sessionManagement(
            smc ->
                smc.invalidSessionUrl("invalidSession")
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true))
        .requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) // Only HTTPS traffic allowed
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user")
                    .authenticated()
                    .requestMatchers("/contact", "/notices", "/error", "/invalidSession")
                    .permitAll()
                    .requestMatchers("/api/v1/customers/register")
                    .permitAll()
                    .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll());
    http.formLogin(Customizer.withDefaults());
    http.httpBasic(
        hbc -> hbc.authenticationEntryPoint(new CustomHttpBasicAuthenticationEntryPoint()));
    http.exceptionHandling( // exceptionHandling() handles exceptions globally
        ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler())
        // .accessDeniedPage("/denied") // Redirect to a custom page: use only with Spring MVC apps
        );
    http.logout(
        logout ->
            logout
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID"));
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public CompromisedPasswordChecker compromisedPasswordChecker() {
    return new HaveIBeenPwnedRestApiPasswordChecker();
  }
}
