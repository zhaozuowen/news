package com.latamnews.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "news_topic_matches")
public class NewsTopicMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private NewsArticle news;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score;
    @Column(name = "match_reason", nullable = false)
    private String matchReason;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
