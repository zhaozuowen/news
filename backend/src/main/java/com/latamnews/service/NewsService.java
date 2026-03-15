package com.latamnews.service;

import com.latamnews.dto.NewsArticleResponse;
import com.latamnews.entity.NewsArticle;
import com.latamnews.repository.NewsArticleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsArticleRepository newsArticleRepository;

    public List<NewsArticleResponse> latestFeed() {
        return newsArticleRepository.findTop50ByOrderByPublishedAtDesc().stream().map(this::toResponse).toList();
    }

    private NewsArticleResponse toResponse(NewsArticle article) {
        String sourceName = article.getRssSource() != null ? article.getRssSource().getName() : "Mock source";
        return new NewsArticleResponse(article.getId(), article.getTitle(), article.getSummary(), sourceName,
                article.getCountryCode(), article.getLanguage(), article.getPublishedAt(), article.getUrl());
    }
}
