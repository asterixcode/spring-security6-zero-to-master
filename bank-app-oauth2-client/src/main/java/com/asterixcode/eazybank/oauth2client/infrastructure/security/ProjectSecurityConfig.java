package com.asterixcode.eazybank.oauth2client.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            (requests) ->
                requests.requestMatchers("/secure").authenticated().anyRequest().permitAll())
        .formLogin(Customizer.withDefaults())
        .oauth2Login(Customizer.withDefaults());

    return http.build();
  }

  /*
  Once oauth2Login(Customizer.withDefaults()); is enabled,
  we need to tell Spring Security which Auth Server to use for OAuth2 login (social login, own auth server, ...)
  See ClientRegistration class.
  */
  @Bean
  ClientRegistrationRepository clientRegistrationRepository() {
    /*
    To leverage Social Logins in Spring, it's possible to leverage a enum class CommonOAuth2Provider.
    This class provides a set of pre-configured OAuth2 client registrations for popular providers
    like Google, Facebook, GitHub, Okta
    */
    ClientRegistration github = gitHubClientRegistration();
    ClientRegistration facebook = facebookClientRegistration();
    return new InMemoryClientRegistrationRepository(github, facebook);
  }

  /*
  The ClientRegistration class is used to configure the OAuth2 client registration.
   */
  private ClientRegistration gitHubClientRegistration() {
    return CommonOAuth2Provider.GITHUB
        // registrationId: "github" will be used to store the client registration in the
        // InMemoryClientRegistrationRepository
        .getBuilder("github")
        // clientId=generated and configured inside GitHub OAuth2 app
        .clientId("")
        .clientSecret("")
        .build();
  }

  private ClientRegistration facebookClientRegistration() {
    return CommonOAuth2Provider.FACEBOOK
        .getBuilder("facebook")
        .clientId("")
        .clientSecret("")
        .build();
  }
}
