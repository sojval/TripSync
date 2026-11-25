package com.example.tripsync.user.dtos;

import com.example.tripsync.user.entity.AccountStatus;
import com.example.tripsync.user.entity.Role;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        AccountStatus status
) {
}
