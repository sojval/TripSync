package com.example.tripsync.user.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 100)
        String fullName,
        @NotBlank(message = "email is required")
        @Size(max = 100)
        String email,
        @NotBlank(message = "password is required")
        @Size(min = 8 ,max = 72)
        String password
) {
}
