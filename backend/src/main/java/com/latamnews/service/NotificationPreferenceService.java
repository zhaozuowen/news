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
        User user = ensureCurrentUser();
        NotificationPreference preference = ensurePreference(user);
        return toResponse(user, preference);
    }

    public NotificationPreferenceResponse update(NotificationPreferenceRequest request) {
        User user = ensureCurrentUser();
        NotificationPreference preference = ensurePreference(user);
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

    private User ensureCurrentUser() {
        return userRepository.findById(auth.currentUserId()).orElseGet(() -> {
            User user = new User();
            user.setGoogleSub("demo-user");
            user.setEmail("demo@latamnews.local");
            user.setDisplayName("Demo User");
            user.setLocale("es-MX");
            user.setCountryCode("MX");
            user.setTimezone("America/Mexico_City");
            return userRepository.save(user);
        });
    }

    private NotificationPreference ensurePreference(User user) {
        NotificationPreference preference = repository.findByUserId(user.getId());
        if (preference != null) {
            return preference;
        }

        NotificationPreference created = new NotificationPreference();
        created.setUser(user);
        created.setQuietHoursStart("22:00");
        created.setQuietHoursEnd("07:00");
        created.setMaxPushPerDay(10);
        created.setDigestMode(NotificationPreference.DigestMode.REALTIME);
        created.setNotificationsEnabled(true);
        return repository.save(created);
    }

    private NotificationPreferenceResponse toResponse(User user, NotificationPreference preference) {
        return new NotificationPreferenceResponse(user.getLocale(), user.getTimezone(), preference.getQuietHoursStart(),
                preference.getQuietHoursEnd(), preference.getMaxPushPerDay(), preference.getDigestMode().name());
    }
}
