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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;

@Profile("local")
@Configuration
public class SecurityConfigurationLocal {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler =
        new CsrfTokenRequestAttributeHandler();

    http
        /* CORS configuration */
        .cors(
            corsConfig ->
                corsConfig.configurationSource(
                    request -> {
                      CorsConfiguration config = new CorsConfiguration();
                      config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                      config.setAllowedMethods(Collections.singletonList("*"));
                      config.setAllowedHeaders(Collections.singletonList("*"));
                      config.setAllowCredentials(true);
                      /* Config headers sent from the backend to the client
                      (e.g., Authorization header in the response headers when using JWT)
                      When the login is completed, the server must generate and send a JWT token to the client.
                       */
                      config.setExposedHeaders(List.of("Authorization"));
                      /* Set the maximum age of the preflight request in seconds */
                      config.setMaxAge(3600L);
                      return config;
                    }))
        //        .csrf(AbstractHttpConfigurer::disable)
        //        .sessionManagement(
        //            smc ->
        //                smc.invalidSessionUrl("/invalidSession")
        //                    .maximumSessions(3)
        //                    .maxSessionsPreventsLogin(true))
        /*
        commented out in favor of configuring the csrf() method as not /invalidSession page is
        configured.
         */
        /* End CORS configuration */
        /* Start CSRF config */
        .csrf(
            csrfConfig ->
                csrfConfig
                    .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                    /* Ignoring CSRF for specific endpoints, usually POST requests */
                    .ignoringRequestMatchers("/contact", "/register", "/apiLogin")
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
        .sessionManagement(
            sessionConfig ->
                sessionConfig.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                    /* STATELESS = Tells Spring Security to never create a session and to never
                     store it in the SecurityContextHolder. This is useful for REST APIs with JWT.
                     The JSESSIONID is not created. The JWT is never stored in the backend but
                    sent to the client.
                    */
                    // .ALWAYS
                    /*  Tells Spring Security to create a session JSESSIONID if one doesn't exist.
                    The JSESSIONID is created but not saved in the SecurityContextHolder, so it
                    requires manual handling.
                    This is done by invoking the .securityContext()
                    */
                    ))
        // .securityContext(
        //    contextConfig ->
        //        contextConfig.requireExplicitSave(
        //            false))
        /* .securityContext(...): This tells the Spring Security that the app is not storing any
        JSESSIONID or the logged in user in the SecurityContextHolder.
        Instead, it tells Spring Security to create a session if one doesn't exist.
        .securityContext is useful for apps that require a session but don't need to store the user in the SecurityContextHolder.
        .requireExplicitSave(false) tells Spring Security to automatically save the SecurityContext
        .requireExplicitSave() is not used with .sessionCreationPolicy(SessionCreationPolicy.STATELESS) as it's not needed for REST APIs with JWT.
        */
        /* End CSRF config */
        /* Filters for demonstration purposes: */
        // .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
        // .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
        // .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
        /* Generate JWT token after basic authentication, only on login endpoint (shouldNotFilter method) */
        .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
        /* Validate JWT token before any request, except login endpoint (shouldNotFilter method) */
        .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
        .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP traffic allowed
        .authorizeHttpRequests(
            requests ->
                requests
                    /* just authentication, no authorization/authorities/roles */
                    /*
                    .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user")
                    .authenticated()
                    */

                    /* authorization with authorities: fine-grained access control */
                    .requestMatchers("/myAccount")
                    .hasAuthority("VIEW_ACCOUNT")
                    .requestMatchers("/myBalance")
                    .hasAnyAuthority("VIEW_BALANCE", "VIEW_ACCOUNT")
                    .requestMatchers("/myLoans")
                    .hasAuthority("VIEW_LOANS")
                    .requestMatchers("/myCards")
                    .hasAuthority("VIEW_CARDS")
                    /* authorization with roles = a group of authorities */
                    /*
                    .requestMatchers("/myAccount")
                    .hasRole("USER")
                    .requestMatchers("/myBalance")
                    .hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/myLoans")
                    .hasRole("USER")
                    .requestMatchers("/myCards")
                    .hasRole("USER")
                     */
                    .requestMatchers("/user")
                    .authenticated()
                    .requestMatchers(
                        "/contact", "/notices", "/error", "/invalidSession", "/apiLogin")
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

  /* Method to use the default JDBC user details manager */
  /*
  @Bean
  UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }
  */

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public CompromisedPasswordChecker compromisedPasswordChecker() {
    return new HaveIBeenPwnedRestApiPasswordChecker();
  }

  @Bean
  AuthenticationManager authenticationManager(
      UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    BankUsernamePasswordAuthenticationProvider authenticationProvider =
        new BankUsernamePasswordAuthenticationProvider(userDetailsService, passwordEncoder);
    /* ProviderManager is a concrete implementation of AuthenticationManager */
    ProviderManager providerManager = new ProviderManager(authenticationProvider);
    /* Set the ProviderManager to not erase credentials after authentication, so that the credentials can be used later.
     * This is useful for using the credentials for any other validation etc inside the application after authentication.
     * providerManager.setEraseCredentialsAfterAuthentication(false);
     * */
    providerManager.setEraseCredentialsAfterAuthentication(false);
    return providerManager;
  }
}
