package com.latamnews.dto;

import jakarta.validation.constraints.NotBlank;

public record DeviceRegistrationRequest(@NotBlank String platform, @NotBlank String deviceName, @NotBlank String fcmToken) {
}
