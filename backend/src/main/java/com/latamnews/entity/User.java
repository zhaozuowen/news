package com.latamnews.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "google_sub", nullable = false, unique = true)
    private String googleSub;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "display_name", nullable = false)
    private String displayName;
    @Column(nullable = false)
    private String locale;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    @Column(nullable = false)
    private String timezone;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
