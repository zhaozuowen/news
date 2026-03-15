package com.latamnews.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_devices")
public class UserDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private String platform;
    @Column(name = "device_name", nullable = false)
    private String deviceName;
    @Column(name = "fcm_token", nullable = false)
    private String fcmToken;
    @Column(nullable = false)
    private Boolean active;
    @Column(name = "last_seen_at")
    private LocalDateTime lastSeenAt;
}
