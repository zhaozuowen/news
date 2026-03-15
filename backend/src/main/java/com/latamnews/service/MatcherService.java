package com.latamnews.service;

import com.latamnews.entity.NewsArticle;
import com.latamnews.entity.NewsTopicMatch;
import com.latamnews.entity.Topic;
import com.latamnews.repository.NewsArticleRepository;
import com.latamnews.repository.NewsTopicMatchRepository;
import com.latamnews.repository.TopicRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MatcherService {
    private final TopicRepository topicRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final NewsTopicMatchRepository matchRepository;

    public void matchDueTopics() {
        var topics = topicRepository.findTop20ByNextMatchAtBeforeOrderBySubscriberCountDesc(LocalDateTime.now());
        var latestArticles = newsArticleRepository.findTop50ByOrderByPublishedAtDesc();
        for (Topic topic : topics) {
            latestArticles.stream()
                    .filter(article -> article.getCountryCode().equalsIgnoreCase(topic.getCountryCode()))
                    .filter(article -> article.getTitle().toLowerCase().contains(topic.getNormalizedText().split(" ")[0]))
                    .forEach(article -> storeMatchIfNeeded(topic, article));
            topic.setNextMatchAt(LocalDateTime.now().plusHours(topic.getHeatLevel() == Topic.HeatLevel.HOT ? 1 : topic.getHeatLevel() == Topic.HeatLevel.WARM ? 6 : 24));
            topicRepository.save(topic);
        }
        log.info("Matched {} due topics against {} articles", topics.size(), latestArticles.size());
    }

    private void storeMatchIfNeeded(Topic topic, NewsArticle article) {
        if (matchRepository.existsByNewsIdAndTopicId(article.getId(), topic.getId())) {
            return;
        }
        NewsTopicMatch match = new NewsTopicMatch();
        match.setTopic(topic);
        match.setNews(article);
        double base = article.getTitle().length() > 80 ? 90 : 75;
        match.setScore(BigDecimal.valueOf(base + (topic.getSubscriberCount() / 100.0)).setScale(2, RoundingMode.HALF_UP));
        match.setMatchReason("Keyword overlap + country match");
        matchRepository.save(match);
    }
}
