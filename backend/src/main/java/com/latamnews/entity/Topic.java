package com.latamnews.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "raw_text", nullable = false)
    private String rawText;
    @Column(name = "normalized_text", nullable = false, unique = true)
    private String normalizedText;
    @Column(nullable = false)
    private String locale;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "heat_level", nullable = false)
    private HeatLevel heatLevel;
    @Column(name = "subscriber_count", nullable = false)
    private Integer subscriberCount;
    @Column(name = "next_match_at")
    private LocalDateTime nextMatchAt;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public enum HeatLevel { HOT, WARM, COLD }
}
