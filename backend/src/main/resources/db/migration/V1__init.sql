CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    google_sub VARCHAR(128) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    display_name VARCHAR(120) NOT NULL,
    locale VARCHAR(16) NOT NULL,
    country_code VARCHAR(8) NOT NULL,
    timezone VARCHAR(64) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE notification_preferences (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL REFERENCES users(id),
    quiet_hours_start VARCHAR(8) NOT NULL,
    quiet_hours_end VARCHAR(8) NOT NULL,
    max_push_per_day INT NOT NULL,
    digest_mode VARCHAR(32) NOT NULL,
    notifications_enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE user_devices (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    platform VARCHAR(32) NOT NULL,
    device_name VARCHAR(120) NOT NULL,
    fcm_token VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    last_seen_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE topics (
    id BIGSERIAL PRIMARY KEY,
    raw_text VARCHAR(255) NOT NULL,
    normalized_text VARCHAR(255) NOT NULL UNIQUE,
    locale VARCHAR(16) NOT NULL,
    country_code VARCHAR(8) NOT NULL,
    heat_level VARCHAR(16) NOT NULL,
    subscriber_count INT NOT NULL DEFAULT 0,
    next_match_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subscriptions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    topic_id BIGINT NOT NULL REFERENCES topics(id),
    notification_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, topic_id)
);

CREATE TABLE rss_sources (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    country_code VARCHAR(8) NOT NULL,
    language VARCHAR(16) NOT NULL,
    feed_url VARCHAR(255) NOT NULL,
    priority INT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE news_articles (
    id BIGSERIAL PRIMARY KEY,
    rss_source_id BIGINT REFERENCES rss_sources(id),
    title VARCHAR(255) NOT NULL,
    summary TEXT,
    content TEXT,
    url VARCHAR(500) NOT NULL UNIQUE,
    language VARCHAR(16) NOT NULL,
    country_code VARCHAR(8) NOT NULL,
    image_url VARCHAR(500),
    published_at TIMESTAMP NOT NULL,
    ingestion_type VARCHAR(32) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE news_topic_matches (
    id BIGSERIAL PRIMARY KEY,
    news_id BIGINT NOT NULL REFERENCES news_articles(id),
    topic_id BIGINT NOT NULL REFERENCES topics(id),
    score NUMERIC(5,2) NOT NULL,
    match_reason VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(news_id, topic_id)
);

CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    topic_id BIGINT REFERENCES topics(id),
    news_id BIGINT REFERENCES news_articles(id),
    title VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    status VARCHAR(32) NOT NULL,
    firebase_message_id VARCHAR(255),
    sent_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (google_sub, email, display_name, locale, country_code, timezone)
VALUES ('mock-google-sub', 'demo@latamnews.local', 'LATAM Demo User', 'es-MX', 'MX', 'America/Mexico_City');

INSERT INTO notification_preferences (user_id, quiet_hours_start, quiet_hours_end, max_push_per_day, digest_mode, notifications_enabled)
VALUES (1, '22:00', '07:00', 8, 'REALTIME', TRUE);

INSERT INTO user_devices (user_id, platform, device_name, fcm_token, active)
VALUES (1, 'WEB', 'Chrome PWA', 'demo-fcm-token', TRUE);

INSERT INTO topics (raw_text, normalized_text, locale, country_code, heat_level, subscriber_count, next_match_at)
VALUES ('inflación en México', 'inflacion mexico', 'es-MX', 'MX', 'WARM', 124, CURRENT_TIMESTAMP + INTERVAL '6 hours'),
       ('energia solar Brasil', 'energia solar brasil', 'pt-BR', 'BR', 'COLD', 40, CURRENT_TIMESTAMP + INTERVAL '24 hours');

INSERT INTO subscriptions (user_id, topic_id, notification_enabled)
VALUES (1, 1, TRUE),
       (1, 2, TRUE);

INSERT INTO rss_sources (name, country_code, language, feed_url, priority, active)
VALUES ('El Universal MX', 'MX', 'es-MX', 'https://www.eluniversal.com.mx/rss.xml', 10, TRUE),
       ('G1 Brasil', 'BR', 'pt-BR', 'https://g1.globo.com/rss/g1/', 9, TRUE);

INSERT INTO news_articles (rss_source_id, title, summary, content, url, language, country_code, image_url, published_at, ingestion_type)
VALUES (1, 'Inflación en México cierra la semana con presión moderada', 'Indicadores muestran ajustes en consumo y energía.', 'Mock article body for Mexico inflation update.', 'https://example.com/news/mx-inflacion', 'es-MX', 'MX', NULL, CURRENT_TIMESTAMP - INTERVAL '2 hours', 'MOCK'),
       (2, 'Energia solar avança no nordeste brasileiro', 'Projetos regionais atraem novos investimentos.', 'Mock article body for Brazil solar energy update.', 'https://example.com/news/br-solar', 'pt-BR', 'BR', NULL, CURRENT_TIMESTAMP - INTERVAL '5 hours', 'MOCK');

INSERT INTO news_topic_matches (news_id, topic_id, score, match_reason)
VALUES (1, 1, 92.50, 'Keyword overlap in title and summary'),
       (2, 2, 88.25, 'Country and energy keywords matched');

INSERT INTO notifications (user_id, topic_id, news_id, title, body, status, firebase_message_id, sent_at)
VALUES (1, 1, 1, 'Nueva noticia sobre inflación en México', 'Encontramos una noticia relevante en El Universal MX.', 'SENT', 'mock-message-1', CURRENT_TIMESTAMP - INTERVAL '90 minutes'),
       (1, 2, 2, 'Nova notícia sobre energia solar Brasil', 'Encontramos uma notícia relevante em G1 Brasil.', 'SENT', 'mock-message-2', CURRENT_TIMESTAMP - INTERVAL '30 minutes');
