# Backend - LATAM News Push

## Tech Stack

- Spring Boot 3.3
- Java 17
- Maven
- PostgreSQL + JPA
- Flyway
- Redis / RabbitMQ placeholders
- Firebase Admin placeholder
- Rome RSS parser

## Run

```bash
mvn spring-boot:run
```

## Notes

- Flyway 会自动初始化表结构和演示数据。
- 当前使用 `MockAuthenticationFacade` 固定返回 demo 用户，便于前端联调。
- Security 暂时开放 API 以便 MVP 开发；上线前必须补齐 JWT 与权限体系。
