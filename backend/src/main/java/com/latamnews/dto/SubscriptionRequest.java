package com.latamnews.dto;

import jakarta.validation.constraints.NotBlank;

public record SubscriptionRequest(@NotBlank String topic, @NotBlank String locale, @NotBlank String countryCode) {
}
