package com.latamnews.repository;

import com.latamnews.entity.Topic;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByNormalizedText(String normalizedText);
    List<Topic> findTop20ByNextMatchAtBeforeOrderBySubscriberCountDesc(LocalDateTime time);
}
