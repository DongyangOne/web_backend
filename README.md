# web_backend

ONE 웹사이트 백엔드입니다.

## 기술 스택

- Java 21 LTS, Spring Boot 4
- Spring Data JPA (Hibernate) + Flyway
- Spring Security + JWT
- MySQL 8.4

## 프로젝트 구조

```text
src/main/java/org/one/
├── domain/                      # 도메인 레이어
│   ├── controller/              # REST 엔드포인트
│   ├── service/                 # 비즈니스 로직
│   ├── entity/                  # JPA 엔티티
│   ├── repository/              # 데이터 액세스
│   ├── dto/
│   │   ├── request/             # 요청 DTO
│   │   └── response/            # 응답 DTO
│   └── enums/                   # 도메인 enum
│
└── global/                      # 공통 인프라
    ├── config/                  # Spring 설정
    │   ├── auth/                # JWT 설정
    │   ├── swagger/             # Swagger/OpenAPI 설정
    │   └── minio/               # MinIO S3 설정
    ├── security/                # JWT 인증 필터 및 JWT 제공자
    ├── exception/               # GlobalExceptionHandler, GlobalErrorController
    ├── dto/                     # ApiResponse 등 공통 응답
    ├── entity/                  # BaseEntity (JPA 감시 필드 기본)
    ├── enums/                   # ErrorCode 등 공통 enum
    └── scheduler/               # 자동 스케줄러
```
