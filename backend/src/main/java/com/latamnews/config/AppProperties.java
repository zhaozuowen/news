package com.latamnews.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record AppProperties(Auth auth, Firebase firebase, Rss rss) {
    public record Auth(String jwtSecret) {}
    public record Firebase(boolean enabled, String serviceAccountPath) {}
    public record Rss(boolean fetchEnabled, List<RssSourceSeed> defaultSources) {
        public Rss {
            defaultSources = defaultSources == null ? new ArrayList<>() : defaultSources;
        }
    }
    public record RssSourceSeed(String countryCode, String name, String language, String url) {}
}
