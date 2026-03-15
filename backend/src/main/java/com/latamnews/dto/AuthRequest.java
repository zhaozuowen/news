package com.latamnews.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank String googleIdToken) {
}
