package com.asterixcode.bankapi.infrastructure.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.*;
import java.util.Arrays;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  private static final String TITLE = "Bank API";
  private static final String DESCRIPTION = "REST API for Bank operations";
  private static final String VERSION = "0.0.1";

  @Bean
  Info apiInfo() {
    return new Info().title(TITLE).description(DESCRIPTION).version(VERSION);
  }

  /* Add Global Header to all endpoints for OpenApi beans */
  /*
  @Bean
  public OpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
    return openApi ->
        openApi.getPaths().values().stream()
            .flatMap(pathItem -> pathItem.readOperations().stream())
            .forEach(
                operation ->
                    operation.addParametersItem(
                        new HeaderParameter()
                            .name("X-Context-ID")
                            .description("Context")
                            .required(true)
                            .schema(new StringSchema())));
  }
  */

  /* Add Global Header to all endpoints for GroupedOpenApi beans */
  @Bean
  public GlobalOpenApiCustomizer customerGlobalHeaderOpenApiCustomizer() {
    return openApi ->
        openApi.getPaths().values().stream()
            .flatMap(pathItem -> pathItem.readOperations().stream())
            .forEach(
                operation ->
                    operation.addParametersItem(
                        new HeaderParameter()
                            .name("X-Context-ID")
                            .description("Context the request is for")
                            .required(true)
                            .schema(new StringSchema()))
                //                        .addParametersItem(
                //                            new HeaderParameter()
                //                                .name("Version")
                //                                .description("version header")
                //                                .required(true)
                //                                .schema(new StringSchema()))
                );
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(apiInfo())
        .components(
            new Components()
                // add basic auth
                .addSecuritySchemes(
                    "basic-auth",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                .addSecuritySchemes(
                    "bearer-jwt",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .description("Bearer JWT Token")
                        .in(SecurityScheme.In.HEADER)
                        .scheme("bearer")
                        .bearerFormat("JWT"))
                .addSecuritySchemes(
                    "api_key",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .description("Api Key access")
                        .in(SecurityScheme.In.HEADER)
                        .name("API-KEY"))
                .addSecuritySchemes(
                    "spring_oauth",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.OAUTH2)
                        .description("Oauth2 flow")
                        .flows(
                            new OAuthFlows()
                                .authorizationCode(
                                    new OAuthFlow()
                                        .authorizationUrl("/auth")
                                        .refreshUrl("/token")
                                        .tokenUrl("/token")
                                        .scopes(new Scopes())))))
        .security(
            Arrays.asList(
                new SecurityRequirement().addList("basic-auth"),
                new SecurityRequirement().addList("bearer-jwt"),
                new SecurityRequirement().addList("api_key"),
                new SecurityRequirement().addList("spring_oauth")));
  }

  @Bean
  public GroupedOpenApi customerApi() {
    return GroupedOpenApi.builder()
        .group("Customers")
        .pathsToMatch("/api/v1/customers/**", "/user")
        .build();
  }
}
