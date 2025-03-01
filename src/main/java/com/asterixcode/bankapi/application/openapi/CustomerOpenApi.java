package com.asterixcode.bankapi.application.openapi;

import com.asterixcode.bankapi.application.model.CustomerModel;
import com.asterixcode.bankapi.application.model.RegisterCustomerRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Customers")
@RequestMapping("/api/v1/customers")
public interface CustomerOpenApi {

  @Operation(
      summary = "Register a customer",
      responses = {
        @ApiResponse(responseCode = "201", description = "Customer registered"),
      })
  @PostMapping(path = "/register")
  CustomerModel register(
      @RequestBody(
              description = "New customer representation",
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = RegisterCustomerRequest.class),
                      examples = {
                        @ExampleObject(
                            name = "admin",
                            value =
                                """
                                {
                                  "email": "admin@mail.com",
                                  "password": "pass@admin@4321",
                                  "role": "admin"
                                }
                                """),
                        @ExampleObject(
                            name = "user",
                            value =
                                """
                                {
                                  "email": "user@mail.com",
                                  "password": "pass@admin@4321",
                                  "role": "read"
                                }
                                """),
                      }))
          RegisterCustomerRequest request);
}
