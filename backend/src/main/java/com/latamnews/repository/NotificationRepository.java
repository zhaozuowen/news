package com.latamnews.repository;

import com.latamnews.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @EntityGraph(attributePaths = {"topic", "news"})
    List<Notification> findTop50ByUserIdOrderByCreatedAtDesc(Long userId);
}
