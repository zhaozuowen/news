package com.latamnews.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(enableKeyspaceEvents = org.springframework.data.redis.core.RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
class RedisConfig {
}
