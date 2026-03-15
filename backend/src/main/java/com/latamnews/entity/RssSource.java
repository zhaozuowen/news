package com.latamnews.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rss_sources")
public class RssSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    @Column(nullable = false)
    private String language;
    @Column(name = "feed_url", nullable = false)
    private String feedUrl;
    @Column(nullable = false)
    private Integer priority;
    @Column(nullable = false)
    private Boolean active;
}
