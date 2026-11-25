package com.example.tripsync.user.service;

import com.example.tripsync.user.dtos.RegisterRequest;
import com.example.tripsync.user.dtos.UserResponse;
import com.example.tripsync.user.entity.AccountStatus;
import com.example.tripsync.user.entity.Role;
import com.example.tripsync.user.entity.User;
import com.example.tripsync.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest registerRequest){
        String email = registerRequest.email().trim();
        if(userRepository.findByEmail(email).isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Email id registered");

        }
        User user = new User();
        user.setFullName(registerRequest.fullName().trim());
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(registerRequest.password()));
        user.setRole(Role.USER);
        user.setAccountStatus(AccountStatus.ACTIVE);
        User savedUser = userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getAccountStatus()
        );
    }

}
