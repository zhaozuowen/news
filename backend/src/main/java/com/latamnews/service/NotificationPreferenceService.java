package com.latamnews.service;

import com.latamnews.dto.NotificationPreferenceRequest;
import com.latamnews.dto.NotificationPreferenceResponse;
import com.latamnews.entity.NotificationPreference;
import com.latamnews.entity.User;
import com.latamnews.repository.NotificationPreferenceRepository;
import com.latamnews.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationPreferenceService {
    private final NotificationPreferenceRepository repository;
    private final UserRepository userRepository;
    private final MockAuthenticationFacade auth;

    public NotificationPreferenceResponse getCurrentSettings() {
        NotificationPreference preference = repository.findByUserId(auth.currentUserId());
        User user = userRepository.findById(auth.currentUserId()).orElseThrow();
        return toResponse(user, preference);
    }

    public NotificationPreferenceResponse update(NotificationPreferenceRequest request) {
        NotificationPreference preference = repository.findByUserId(auth.currentUserId());
        User user = userRepository.findById(auth.currentUserId()).orElseThrow();
        user.setLocale(request.locale());
        user.setTimezone(request.timezone());
        userRepository.save(user);
        preference.setQuietHoursStart(request.quietHoursStart());
        preference.setQuietHoursEnd(request.quietHoursEnd());
        preference.setMaxPushPerDay(request.maxPushPerDay());
        preference.setDigestMode(request.digestMode());
        repository.save(preference);
        return toResponse(user, preference);
    }

    private NotificationPreferenceResponse toResponse(User user, NotificationPreference preference) {
        return new NotificationPreferenceResponse(user.getLocale(), user.getTimezone(), preference.getQuietHoursStart(),
                preference.getQuietHoursEnd(), preference.getMaxPushPerDay(), preference.getDigestMode().name());
    }
}
