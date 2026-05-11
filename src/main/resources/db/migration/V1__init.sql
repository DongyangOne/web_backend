-- 1. 관리자 (단일 계정)
CREATE TABLE `admin` (
    `admin_id`   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`   VARCHAR(50)  NOT NULL UNIQUE,
    `password`   VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 2. 메인 페이지 설정 (싱글톤 - main_id 항상 1)
CREATE TABLE `main_page` (
    `main_id`           INT       NOT NULL DEFAULT 1 PRIMARY KEY,
    `logo_url`          VARCHAR(500)        DEFAULT NULL,
    `description`       TEXT                DEFAULT NULL,
    `recruitment_start` DATE                DEFAULT NULL,
    `recruitment_end`   DATE                DEFAULT NULL,
    `updated_at`        TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. 메인 페이지 노출용 프로젝트 (메인페이지 1 : N 프로젝트)
CREATE TABLE `project_event` (
    `project_id`        BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `main_id`           INT          NOT NULL DEFAULT 1,
    `project_name`      VARCHAR(100) NOT NULL,
    `participant_count` INT          NOT NULL DEFAULT 0,
    `description`       TEXT                  DEFAULT NULL,
    `priority`          INT          NOT NULL DEFAULT 0,
    `created_at`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_project_main` FOREIGN KEY (`main_id`) REFERENCES `main_page` (`main_id`)
);

-- 4. 프로젝트 사진 (프로젝트 1 : N 사진)
CREATE TABLE `project_photo` (
    `photo_id`   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `project_id` BIGINT       NOT NULL,
    `photo_url`  VARCHAR(500) NOT NULL,
    `priority`   INT          NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_photo_project` FOREIGN KEY (`project_id`) REFERENCES `project_event` (`project_id`) ON DELETE CASCADE
);

-- 5. 신청 부원 (1년 뒤 하드 딜리트 대상)
CREATE TABLE `applicant_member` (
    `applicant_id`     BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`             VARCHAR(100) NOT NULL,
    `department`       VARCHAR(100) NOT NULL,
    `student_id`       VARCHAR(8)   NOT NULL,
    `birthday`         DATE         NOT NULL,
    `grade`            INT          NOT NULL,
    `phone_number`     VARCHAR(20)  NOT NULL,
    `gender`           ENUM('MALE', 'FEMALE') NOT NULL,
    `motivation`       TEXT         NOT NULL,
    `tech_stack`       TEXT         NOT NULL,
    `desired_activity` TEXT         NOT NULL,
    `final_words`      TEXT                   DEFAULT NULL,
    `privacy_consent`  BOOLEAN      NOT NULL   DEFAULT FALSE,
    `is_first_view`    BOOLEAN      NOT NULL   DEFAULT TRUE,
    `created_at`       TIMESTAMP    NOT NULL   DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_applicant_created_at` (`created_at`)
);

-- 6. 정규 부원 (매년 학년/나이 업데이트 대상)
CREATE TABLE `member` (
    `member_id`         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `status`            ENUM('ACTIVE', 'MILITARY_LEAVE', 'GRADUATED', 'WITHDRAWN', 'ON_LEAVE')
                                     NOT NULL DEFAULT 'ACTIVE',
    `name`              VARCHAR(100) NOT NULL,
    `department`        VARCHAR(100) NOT NULL,
    `student_id`        VARCHAR(8)   NOT NULL UNIQUE,
    `birthday`          DATE         NOT NULL,
    `grade`             INT          NOT NULL,
    `age`               INT          NOT NULL,
    `phone_number`      VARCHAR(20)  NOT NULL,
    `gender`            ENUM('MALE', 'FEMALE') NOT NULL,
    `registered_at`     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_promotion_at` DATETIME              DEFAULT NULL,
    INDEX `idx_member_status` (`status`)
);

-- 7. 캘린더 일정 (기간제)
CREATE TABLE `calendar_schedule` (
    `calendar_id` BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title`       VARCHAR(100) NOT NULL,
    `start_date`  DATE         NOT NULL,
    `end_date`    DATE         NOT NULL,
    `description` VARCHAR(255)          DEFAULT NULL,
    `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `chk_calendar_dates` CHECK (`end_date` >= `start_date`),
    INDEX `idx_calendar_dates` (`start_date`, `end_date`)
);

-- 8. 리프레시 토큰
CREATE TABLE `refresh_token` (
    `id`         BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id`    BIGINT       NOT NULL,
    `token_hash` VARCHAR(128) NOT NULL UNIQUE,
    `expires_at` DATETIME     NOT NULL,
    `revoked`    BOOLEAN      NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_refresh_token_user` (`user_id`),
    CONSTRAINT `fk_refresh_token_user` FOREIGN KEY (`user_id`) REFERENCES `admin` (`admin_id`) ON DELETE CASCADE
);
