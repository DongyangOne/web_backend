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
├── domain/                  # 도메인 레이어
│   ├── admin/               # 관리자 계정
│   ├── auth/                # 로그인 (POST /auth/login)
│   ├── applicant/          # 신청 부원
│   ├── calendar/            # 캘린더 일정
│   ├── mainpage/            # 메인 페이지 설정
│   ├── member/              # 정규 부원
│   └── project/             # 프로젝트/사진
└── global/                  # 공통 인프라
    ├── apiPayload/          # 응답/예외 처리
    ├── config/              # Spring 설정
    ├── entity/              # BaseEntity
    ├── file/                # 파일 업로드
    ├── scheduler/           # 자동 스케줄러
    └── security/            # JWT 인증
```
