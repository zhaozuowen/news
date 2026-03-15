package com.latamnews.dto;

public record NotificationPreferenceResponse(String locale, String timezone, String quietHoursStart,
                                             String quietHoursEnd, Integer maxPushPerDay, String digestMode) {
}
