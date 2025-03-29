package com.asterixcode.eazybank.bankapi.infrastructure.springdoc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.*;
import java.util.Arrays;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {

  @Bean
  public GroupedOpenApi customerApi() {
    return GroupedOpenApi.builder()
        .group("Customers")
        .pathsToMatch("/api/v1/customers/**", "/user")
        .build();
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
  public OpenAPI customOpenAPI(SwaggerProperties swaggerProperties) {
    return new OpenAPI()
        .info(apiInfo(swaggerProperties))
        .components(createComponents())
        .security(
            Arrays.asList(
                new SecurityRequirement().addList("basic-auth"),
                new SecurityRequirement().addList("bearer-jwt"),
                new SecurityRequirement().addList("api_key")));
  }

  @Bean
  Info apiInfo(SwaggerProperties swaggerProperties) {
    return new Info()
        .title(swaggerProperties.projectTitle())
        .description(swaggerProperties.projectDescription())
        .version(swaggerProperties.projectVersion())
        .license(getLicense());
  }

  private License getLicense() {
    return new License().name("Unlicense").url("https://unlicense.org/");
  }

  private Components createComponents() {
    return new Components()
        .addSecuritySchemes("basic-auth", createBasicAuthScheme())
        .addSecuritySchemes("bearer-jwt", createBearerJwtScheme())
        .addSecuritySchemes("api_key", createApiKeyScheme());
  }

  private SecurityScheme createBasicAuthScheme() {
    return new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic");
  }

  private SecurityScheme createBearerJwtScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .description("Bearer JWT Token")
        .in(SecurityScheme.In.HEADER)
        .scheme("bearer")
        .bearerFormat("JWT");
  }

  private SecurityScheme createApiKeyScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.APIKEY)
        .description("Api Key access")
        .in(SecurityScheme.In.HEADER)
        .name("API-KEY");
  }
}
