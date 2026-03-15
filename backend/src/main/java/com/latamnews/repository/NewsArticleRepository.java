package com.latamnews.repository;

import com.latamnews.entity.NewsArticle;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findTop50ByOrderByPublishedAtDesc();
    boolean existsByUrl(String url);
    List<NewsArticle> findByPublishedAtAfter(LocalDateTime cutoff);
}
