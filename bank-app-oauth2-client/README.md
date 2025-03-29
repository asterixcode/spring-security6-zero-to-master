# bank-app-oauth2-client

## Description

This is a simple OAuth2 client example application that allows users to authenticate using their social media accounts (
GitHub, Facebook).

## Features

- OAuth2 authentication with GitHub and Facebook

## Configuration

### GitHub

- Register your application on GitHub and obtain the client ID and client secret.

1. Go to Settings > Developer settings > OAuth Apps > Register a new OAuth application.

2. Set the Application name, Homepage URL, and Authorization callback URL.

Example:

- Application name: `bank-app-oauth2-client`
- Homepage URL: `http://localhost:8080` (or your production URL)
- Authorization callback URL: `http://localhost:8080`

3. After registering, you will receive a client ID and will need to generate a new client secret.

4. Add the client ID and client secret

Option 1: to the ClientRegistrationRepository bean:

```java

@Bean
private ClientRegistration gitHubClientRegistration() {
  return CommonOAuth2Provider.GITHUB
      .getBuilder("github")
      .clientId("your-github-oauth-app-client-id")
      .clientSecret("your-github-oauth-app-client-secret")
      .build();
}
```

Option 2: over application properties, which is the recommended way:

Spring Security OAuth2 client will automatically set up the ClientRegistrationRepository bean with the
CommonOAuth2Provider and ClientRegistration behind the scenes.

```properties
spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID}
spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET}
```

### Facebook

- Register your application on Facebook and obtain the client ID and client secret.

1. Go to Facebook for Developers and create a new app

Link URL: https://developers.facebook.com/apps/

Select "Create App" and choose the use case "Authenticate and request data from users with Facebook Login".

2. Set the App name, App contact email

3. After creating the app, go to the "Use cases" section and click on "Customize"

Under Permissions, select and make sure "email" and "public_profile" are enabled as Spring Security requires and use
them to fetch the user information.

NOTE: Facebook calls it "Permissions" instead of the OAuth2 term "Scopes".

4. Go to the "App settings" > "Basic" section and add the following information

Copy the App ID (client_id) and App Secret (client_secret) and add to the application:

Option 1: to the ClientRegistrationRepository bean manually:

```java

@Bean
private ClientRegistration facebookClientRegistration() {
  return CommonOAuth2Provider.FACEBOOK
      .getBuilder("facebook")
      .clientId("app-id")
      .clientSecret("app-secret")
      .build();
}
```

Option 2: over application properties, which is the recommended way:

Spring Security OAuth2 client will automatically set up the ClientRegistrationRepository bean with the
CommonOAuth2Provider and ClientRegistration behind the scenes.

```properties
spring.security.oauth2.client.registration.facebook.client-id=${FACEBOOK_APP_ID}
spring.security.oauth2.client.registration.facebook.client-secret=${FACEBOOK_APP_SECRET}
```

5. Final ClientRegistrationRepository bean configuration example

```java

@Bean
ClientRegistrationRepository clientRegistrationRepository() {
  ClientRegistration github = gitHubClientRegistration();
  ClientRegistration facebook = facebookClientRegistration();
  return new InMemoryClientRegistrationRepository(github, facebook);
}
```

## Drawbacks of OAuth2 social logins

OAuth2 social logins only help with authentication, not authorization.

You will need to implement your own authorization logic to restrict access to certain resources based on user roles or
permissions.

If you want to implement authorization with roles (e.g. admin, user, manager, supervisor, etc) and authorities,
it's not possible to create and maintain it by login into the social login auth servers.

Therefore, this approach might work only for simple apps such as Blogs, SPA, etc. where you don't need to maintain
roles and permissions.

For serious Enterprise applications, you should consider creating/using a own/custom Auth Server,
where we have complete control on user creation, roles, permissions, etc.