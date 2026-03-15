package com.latamnews.dto;

public record AuthResponse(Long userId, String email, String displayName, String locale, String jwtToken) {
}
