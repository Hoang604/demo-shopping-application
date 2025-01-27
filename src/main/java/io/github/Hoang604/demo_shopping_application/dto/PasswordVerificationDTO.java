package io.github.Hoang604.demo_shopping_application.dto;

import jakarta.validation.constraints.NotBlank;

public record PasswordVerificationDTO(
        @NotBlank(message = "Password is required") String password,
    Integer id
) {}
