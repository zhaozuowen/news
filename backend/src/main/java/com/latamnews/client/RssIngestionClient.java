package com.latamnews.client;

import com.latamnews.entity.NewsArticle;
import com.latamnews.entity.RssSource;
import com.latamnews.repository.NewsArticleRepository;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@Slf4j
@Component
@RequiredArgsConstructor
public class RssIngestionClient {
    private final NewsArticleRepository newsArticleRepository;

    public int fetchFromSource(RssSource source) {
        try (XmlReader reader = new XmlReader(new URL(source.getFeedUrl()))) {
            var feed = new SyndFeedInput().build(reader);
            int saved = 0;
            for (SyndEntry entry : feed.getEntries().stream().limit(3).toList()) {
                if (newsArticleRepository.existsByUrl(entry.getLink())) {
                    continue;
                }
                NewsArticle article = new NewsArticle();
                article.setRssSource(source);
                article.setTitle(entry.getTitle());
                article.setSummary(entry.getDescription() != null ? entry.getDescription().getValue() : "RSS summary placeholder");
                article.setContent(article.getSummary());
                article.setUrl(entry.getLink());
                article.setLanguage(source.getLanguage());
                article.setCountryCode(source.getCountryCode());
                article.setPublishedAt(entry.getPublishedDate() != null
                        ? entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        : LocalDateTime.now());
                article.setIngestionType(NewsArticle.IngestionType.RSS);
                newsArticleRepository.save(article);
                saved++;
            }
            return saved;
        } catch (Exception ex) {
            log.warn("RSS ingestion failed for {}: {}", source.getName(), ex.getMessage());
            return 0;
        }
    }
}
