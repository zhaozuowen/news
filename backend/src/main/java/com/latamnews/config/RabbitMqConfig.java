package com.latamnews.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String NOTIFICATION_QUEUE = "latam.notifications";

    @Bean
    Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE, true);
    }
}
