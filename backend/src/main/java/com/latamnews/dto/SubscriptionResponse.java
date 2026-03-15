package com.latamnews.dto;

public record SubscriptionResponse(Long id, String topic, String locale, String countryCode, boolean notificationsEnabled, String heatLevel) {
}
