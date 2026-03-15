package com.latamnews.repository;

import com.latamnews.entity.NotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Long> {
    NotificationPreference findByUserId(Long userId);
}
