# Spring Security 6

## About

Repository for studying Spring Security 6 with Spring Boot 3.

## References

Parts of this project are based on the Udemy course [Spring Security Zero to Master along with JWT,OAUTH2
](https://www.udemy.com/course/spring-security-zero-to-master).

Course repository:
https://github.com/eazybytes/spring-security

## Topics

- [x] Spring Security Architecture, Configuration, Filters
- [x] SecurityFilterChain
- [x] Encode/Decode, Encrypt/Decrypt, Hashing
- [x] Password Encoders
- [x] CompromisedPasswordChecker: Have I Been Pwned
- [x] JdbcUserDetailsManager
- [x] Custom UserDetailsService
- [x] Custom AuthenticationProvider
- [x] Custom HTTP Basic Authentication Entry Point for 401 Unauthorized between microservices
- [x] Custom AccessDeniedHandler for 403 Forbidden
- [x] Custom AuthenticationSuccessEvent and AbstractAuthenticationFailureEvent handlers
- [x] Session Management:
  - Invalid Session URL redirect: `.invalidSessionUrl("/invalid-session")`
  - Concurrent Session Control: `.maximumSessions(3)`
  - Session Fixation Protection `.maxSessionsPreventsLogin(true)`
- [x] Login
- [x] Logout: redirect, invalidate session, clear cookies
- [x] SecurityContextHolder, SecurityContext, Authentication
- [x] HTTPS allowed traffic only
  - `.requiresChannel(rcc -> rcc.anyRequest().requiresSecure())`
- [x] HTTP allowed traffic only
  - `.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())`
- [x] CSRF Protection: Cross Site Request Forgery
- [x] CORS: Cross Origin Resource Sharing:
  - allowedOrigins, allowedMethods, allowedHeaders, exposedHeaders, allowCredentials, maxAge


## Getting Started

The following user is available for testing:

- email: admin@mail.com
- password: pass@user@4321