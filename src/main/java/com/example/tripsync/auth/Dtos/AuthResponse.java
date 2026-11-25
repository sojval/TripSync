package com.example.tripsync.auth.Dtos;

public record AuthResponse(
        String accessToken,

        String tokenType,
        long expireIn
) {
}
