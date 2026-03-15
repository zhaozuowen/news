package com.latamnews.dto;

public record DeviceResponse(Long id, String platform, String deviceName, String fcmToken, boolean active) {
}
