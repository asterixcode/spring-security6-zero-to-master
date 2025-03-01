package com.asterixcode.bankapi.core.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  private static final String TITLE = "Bank API";
  private static final String DESCRIPTION = "REST API for Bank operations";
  private static final String VERSION = "0.0.1";

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(new Info().title(TITLE).description(DESCRIPTION).version(VERSION));
  }

  @Bean
  public GroupedOpenApi customerApi() {
    return GroupedOpenApi.builder().group("Customers").pathsToMatch("/api/v1/customers/**").build();
  }
}
