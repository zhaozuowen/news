package com.latamnews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "quiet_hours_start", nullable = false)
    private String quietHoursStart;
    @Column(name = "quiet_hours_end", nullable = false)
    private String quietHoursEnd;
    @Column(name = "max_push_per_day", nullable = false)
    private Integer maxPushPerDay;
    @Column(name = "digest_mode", nullable = false)
    @Enumerated(EnumType.STRING)
    private DigestMode digestMode;
    @Column(name = "notifications_enabled", nullable = false)
    private Boolean notificationsEnabled;

    public enum DigestMode { REALTIME, DIGEST }
}
