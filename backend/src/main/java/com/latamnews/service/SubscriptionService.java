package com.latamnews.service;

import com.latamnews.dto.SubscriptionRequest;
import com.latamnews.dto.SubscriptionResponse;
import com.latamnews.entity.Subscription;
import com.latamnews.entity.Topic;
import com.latamnews.entity.User;
import com.latamnews.repository.SubscriptionRepository;
import com.latamnews.repository.TopicRepository;
import com.latamnews.repository.UserRepository;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final MockAuthenticationFacade auth;

    public List<SubscriptionResponse> listSubscriptions() {
        return subscriptionRepository.findByUserId(auth.currentUserId()).stream()
                .map(this::toResponse)
                .toList();
    }

    public SubscriptionResponse createSubscription(SubscriptionRequest request) {
        User user = ensureCurrentUser(request.locale(), request.countryCode());
        String normalized = normalize(request.topic());
        Topic topic = topicRepository.findByNormalizedText(normalized).orElseGet(() -> {
            Topic created = new Topic();
            created.setRawText(request.topic());
            created.setNormalizedText(normalized);
            created.setLocale(request.locale());
            created.setCountryCode(request.countryCode());
            created.setSubscriberCount(0);
            created.setHeatLevel(Topic.HeatLevel.COLD);
            created.setNextMatchAt(LocalDateTime.now().plusHours(24));
            return topicRepository.save(created);
        });
        topic.setSubscriberCount(topic.getSubscriberCount() + 1);
        topic.setHeatLevel(resolveHeat(topic.getSubscriberCount()));
        topicRepository.save(topic);

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setTopic(topic);
        subscription.setNotificationEnabled(true);
        return toResponse(subscriptionRepository.save(subscription));
    }

    public void deleteSubscription(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    private SubscriptionResponse toResponse(Subscription subscription) {
        Topic topic = subscription.getTopic();
        return new SubscriptionResponse(subscription.getId(), topic.getRawText(), topic.getLocale(), topic.getCountryCode(),
                subscription.getNotificationEnabled(), topic.getHeatLevel().name());
    }

    private User ensureCurrentUser(String locale, String countryCode) {
        return userRepository.findById(auth.currentUserId()).orElseGet(() -> {
            User user = new User();
            user.setGoogleSub("demo-user");
            user.setEmail("demo@latamnews.local");
            user.setDisplayName("Demo User");
            user.setLocale(locale);
            user.setCountryCode(countryCode);
            user.setTimezone("America/Mexico_City");
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        });
    }

    private String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\s]", " ")
                .trim()
                .replaceAll("\\s+", " ")
                .toLowerCase();
    }

    private Topic.HeatLevel resolveHeat(int subscribers) {
        if (subscribers >= 1000) return Topic.HeatLevel.HOT;
        if (subscribers >= 100) return Topic.HeatLevel.WARM;
        return Topic.HeatLevel.COLD;
    }
}
