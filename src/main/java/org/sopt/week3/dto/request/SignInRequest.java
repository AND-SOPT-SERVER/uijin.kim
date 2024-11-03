package org.sopt.week3.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
