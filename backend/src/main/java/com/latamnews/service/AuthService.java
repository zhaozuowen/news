package com.latamnews.service;

import com.latamnews.dto.AuthRequest;
import com.latamnews.dto.AuthResponse;
import com.latamnews.entity.User;
import com.latamnews.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail("demo@latamnews.local").orElseThrow();
        String token = "mock-jwt-for-" + request.googleIdToken().hashCode();
        return new AuthResponse(user.getId(), user.getEmail(), user.getDisplayName(), user.getLocale(), token);
    }
}
