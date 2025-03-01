package com.asterixcode.bankapi.application.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterCustomerRequest(
    @Schema(example = "user@mail.com") @Email @NotBlank String email,
    @Schema(example = "pass@admin@4321", type = "string") @NotBlank String password,
    @Schema(example = "read") @NotBlank String role) {}
