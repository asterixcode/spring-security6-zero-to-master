package com.asterixcode.bankapi.infrastructure.security;

import com.asterixcode.bankapi.infrastructure.exception.CustomAccessDeniedHandler;
import com.asterixcode.bankapi.infrastructure.exception.CustomHttpBasicAuthenticationEntryPoint;
import com.asterixcode.bankapi.infrastructure.security.filter.CsrfCookieFilter;
import com.asterixcode.bankapi.infrastructure.security.filter.JWTTokenGeneratorFilter;
import com.asterixcode.bankapi.infrastructure.security.filter.JWTTokenValidatorFilter;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

@Profile("!local")
@Configuration
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler =
        new CsrfTokenRequestAttributeHandler();

    http.cors(
            corsConfig ->
                corsConfig.configurationSource(
                    request -> {
                      CorsConfiguration config = new CorsConfiguration();
                      config.setAllowedOrigins(Collections.singletonList("https://localhost:4200"));
                      config.setAllowedMethods(Collections.singletonList("*"));
                      config.setAllowedHeaders(Collections.singletonList("*"));
                      config.setAllowCredentials(true);
                      config.setExposedHeaders(List.of("Authorization"));
                      config.setMaxAge(3600L);
                      return config;
                    }))
        //        .csrf(AbstractHttpConfigurer::disable)
        //        .sessionManagement(
        //            smc ->
        //                smc.invalidSessionUrl("invalidSession")
        //                    .maximumSessions(1)
        //                    .maxSessionsPreventsLogin(true))
        .csrf(
            csrfConfig ->
                csrfConfig
                    .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                    .ignoringRequestMatchers("/contact", "/register")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        /* Config for stateless app with no session management, no JSESSIONID cookie. Use with JWT */
        .sessionManagement(
            sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        /* Config for using JSESSIONID cookie
        .sessionManagement(
            sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
        .securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
        */
        .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
        .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
        .requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) // Only HTTPS traffic allowed
        .authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers("/myAccount")
                    .hasAuthority("VIEW_ACCOUNT")
                    .requestMatchers("/myBalance")
                    .hasAnyAuthority("VIEW_BALANCE", "VIEW_ACCOUNT")
                    .requestMatchers("/myLoans")
                    .hasAuthority("VIEW_LOANS")
                    .requestMatchers("/myCards")
                    .hasAuthority("VIEW_CARDS")
                    .requestMatchers("/user")
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
