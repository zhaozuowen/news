package com.latamnews.dto;

import java.time.LocalDateTime;

public record NotificationHistoryResponse(Long id, String title, String body, String status, LocalDateTime sentAt, String topic) {
}
