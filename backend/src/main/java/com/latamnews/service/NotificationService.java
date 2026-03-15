package com.latamnews.service;

import com.latamnews.dto.NotificationHistoryResponse;
import com.latamnews.entity.NewsArticle;
import com.latamnews.entity.Notification;
import com.latamnews.entity.Subscription;
import com.latamnews.repository.NotificationRepository;
import com.latamnews.repository.SubscriptionRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import static com.latamnews.config.RabbitMqConfig.NOTIFICATION_QUEUE;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MockAuthenticationFacade auth;
    private final ObjectProvider<RabbitTemplate> rabbitTemplateProvider;

    public List<NotificationHistoryResponse> history() {
        return notificationRepository.findTop50ByUserIdOrderByCreatedAtDesc(auth.currentUserId()).stream()
                .map(notification -> new NotificationHistoryResponse(notification.getId(), notification.getTitle(), notification.getBody(),
                        notification.getStatus().name(), notification.getSentAt(), notification.getTopic() != null ? notification.getTopic().getRawText() : "General"))
                .toList();
    }

    public void queueNotificationsForArticle(NewsArticle article) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(auth.currentUserId());
        subscriptions.stream()
                .filter(subscription -> article.getTitle().toLowerCase().contains(subscription.getTopic().getCountryCode().toLowerCase())
                        || article.getSummary().toLowerCase().contains(subscription.getTopic().getRawText().split(" ")[0].toLowerCase()))
                .forEach(subscription -> {
                    Notification notification = new Notification();
                    notification.setUser(subscription.getUser());
                    notification.setTopic(subscription.getTopic());
                    notification.setNews(article);
                    notification.setTitle("New article for " + subscription.getTopic().getRawText());
                    notification.setBody(article.getTitle());
                    notification.setStatus(Notification.Status.PENDING);
                    notification.setCreatedAt(LocalDateTime.now());
                    Notification saved = notificationRepository.save(notification);
                    RabbitTemplate rabbitTemplate = rabbitTemplateProvider.getIfAvailable();
                    if (rabbitTemplate != null) {
                        rabbitTemplate.convertAndSend(NOTIFICATION_QUEUE, "notification:" + saved.getId());
                    }
                });
    }
}
