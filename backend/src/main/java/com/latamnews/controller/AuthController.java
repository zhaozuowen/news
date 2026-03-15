package com.latamnews.controller;

import com.latamnews.dto.AuthRequest;
import com.latamnews.dto.AuthResponse;
import com.latamnews.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/google")
    public AuthResponse googleLogin(@Valid @RequestBody AuthRequest request) {
        return authService.authenticate(request);
    }
}
