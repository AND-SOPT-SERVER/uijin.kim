package org.sopt.week3.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        String nickname
) {
}
