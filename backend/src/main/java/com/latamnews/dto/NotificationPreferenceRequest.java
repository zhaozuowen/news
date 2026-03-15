package com.latamnews.dto;

import com.latamnews.entity.NotificationPreference.DigestMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationPreferenceRequest(@NotBlank String locale, @NotBlank String timezone,
                                            @NotBlank String quietHoursStart, @NotBlank String quietHoursEnd,
                                            @NotNull Integer maxPushPerDay, @NotNull DigestMode digestMode) {
}
