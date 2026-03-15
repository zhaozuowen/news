package com.latamnews.repository;

import com.latamnews.entity.NewsTopicMatch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTopicMatchRepository extends JpaRepository<NewsTopicMatch, Long> {
    List<NewsTopicMatch> findByTopicId(Long topicId);
    boolean existsByNewsIdAndTopicId(Long newsId, Long topicId);
}
