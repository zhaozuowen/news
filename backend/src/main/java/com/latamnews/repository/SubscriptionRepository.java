package com.latamnews.repository;

import com.latamnews.entity.Subscription;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @EntityGraph(attributePaths = {"topic"})
    List<Subscription> findByUserId(Long userId);
}
