package com.latamnews.dto;

import java.time.LocalDateTime;

public record NewsArticleResponse(Long id, String title, String summary, String sourceName, String countryCode,
                                  String language, LocalDateTime publishedAt, String url) {
}
