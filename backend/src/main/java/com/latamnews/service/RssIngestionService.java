package com.latamnews.service;

import com.latamnews.client.RssIngestionClient;
import com.latamnews.entity.NewsArticle;
import com.latamnews.entity.RssSource;
import com.latamnews.repository.NewsArticleRepository;
import com.latamnews.repository.RssSourceRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RssIngestionService {
    private final RssSourceRepository rssSourceRepository;
    private final RssIngestionClient rssIngestionClient;
    private final NewsArticleRepository newsArticleRepository;
    private final NotificationService notificationService;

    public void seedMockArticleIfNeeded() {
        if (!newsArticleRepository.findByPublishedAtAfter(LocalDateTime.now().minusHours(12)).isEmpty()) {
            return;
        }
        log.info("Recent news exists, skip mock seeding.");
    }

    public void fetchLatest() {
        for (RssSource source : rssSourceRepository.findByActiveTrueOrderByPriorityDesc()) {
            int saved = rssIngestionClient.fetchFromSource(source);
            log.info("Fetched {} new articles from {}", saved, source.getName());
        }
        newsArticleRepository.findTop50ByOrderByPublishedAtDesc().stream().limit(3).forEach(notificationService::queueNotificationsForArticle);
    }
}
