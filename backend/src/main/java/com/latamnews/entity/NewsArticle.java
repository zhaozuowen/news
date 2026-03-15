package com.latamnews.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "news_articles")
public class NewsArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rss_source_id")
    private RssSource rssSource;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String summary;
    @Column(columnDefinition = "TEXT")
    private String content;
    @Column(nullable = false, unique = true, length = 500)
    private String url;
    @Column(nullable = false)
    private String language;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "published_at", nullable = false)
    private LocalDateTime publishedAt;
    @Column(name = "ingestion_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private IngestionType ingestionType;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum IngestionType { MOCK, RSS }
}
