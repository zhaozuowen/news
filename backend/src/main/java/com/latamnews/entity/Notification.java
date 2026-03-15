package com.latamnews.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id")
    private NewsArticle news;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "firebase_message_id")
    private String firebaseMessageId;
    @Column(name = "sent_at")
    private LocalDateTime sentAt;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum Status { PENDING, SENT, FAILED }
}
