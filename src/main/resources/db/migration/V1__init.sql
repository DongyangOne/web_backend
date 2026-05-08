-- 1. 관리자 (단일 계정)
CREATE TABLE `admin` (
    `admin_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. 메인 페이지 설정
CREATE TABLE `main_page` (
    `main_id` INT PRIMARY KEY DEFAULT 1,
    `logo_url` VARCHAR(500) COMMENT '로고 이미지 경로',
    `description` TEXT COMMENT '동아리 소개글',
    `recruitment_start` DATE COMMENT '모집 시작일',
    `recruitment_end` DATE COMMENT '모집 종료일',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. 메인 페이지 노출용 프로젝트 (메인페이지 1 : N 프로젝트)
CREATE TABLE `project_event` (
    `project_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `main_id` INT DEFAULT 1 COMMENT '메인페이지 연결',
    `project_name` VARCHAR(100) NOT NULL,
    `participant_count` INT DEFAULT 0,
    `description` TEXT,
    `priority` INT DEFAULT 0 COMMENT '프로젝트 노출 우선순위 (낮을수록 상단)',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_project_main` FOREIGN KEY (`main_id`) REFERENCES `main_page` (`main_id`)
);

-- 4. 프로젝트 사진 (프로젝트 1 : N 사진)
CREATE TABLE `project_photo` (
    `photo_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `project_id` BIGINT NOT NULL,
    `photo_url` VARCHAR(500) NOT NULL,
    `priority` INT DEFAULT 0 COMMENT '사진 노출 순서 (0번이 대표 이미지)',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_photo_project` FOREIGN KEY (`project_id`) REFERENCES `project_event` (`project_id`) ON DELETE CASCADE
);

-- 5. 신청 부원 (1년 뒤 하드 딜리트 대상)
CREATE TABLE `applicant_member` (
    `applicant_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL,
    `department` VARCHAR(100) NOT NULL,
    `student_id` VARCHAR(8) NOT NULL,
    `birthday` DATE NOT NULL,
    `grade` INT NOT NULL,
    `phone_number` VARCHAR(20) NOT NULL,
    `gender` VARCHAR(10) NOT NULL,
    `motivation` TEXT NOT NULL COMMENT '지원동기',
    `tech_stack` TEXT NOT NULL COMMENT '사용 언어 및 라이브러리',
    `desired_activity` TEXT NOT NULL COMMENT '동아리에서 해보고 싶은 것',
    `final_words` TEXT COMMENT '마지막으로 하고 싶은 말(비필수)',
    `privacy_consent` BOOLEAN NOT NULL DEFAULT FALSE,
    `is_first_view` BOOLEAN DEFAULT TRUE COMMENT '관리자 최초 조회 여부',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 6. 정규 부원 (매년 학년/나이 업데이트 대상)
CREATE TABLE `member` (
    `member_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `status` VARCHAR(20) DEFAULT '활동' COMMENT '재학, 군휴학, 졸업 등',
    `name` VARCHAR(100) NOT NULL,
    `department` VARCHAR(100) NOT NULL,
    `student_id` VARCHAR(8) NOT NULL UNIQUE,
    `birthday` DATE NOT NULL,
    `grade` INT NOT NULL COMMENT '학년(매년 +1)',
    `age` INT NOT NULL COMMENT '나이(매년 +1)',
    `phone_number` VARCHAR(20) NOT NULL,
    `gender` VARCHAR(10) NOT NULL,
    `registered_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_promotion_at` DATETIME NULL COMMENT '마지막 학년 승급 일시'
);

-- 7. 캘린더 일정 (기간제)
CREATE TABLE `calendar_schedule` (
    `calendar_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `title` VARCHAR(100) NOT NULL,
    `start_date` DATE NOT NULL,
    `end_date` DATE NOT NULL COMMENT '하루 일정은 start_date와 동일하게 설정',
    `description` VARCHAR(255) COMMENT '짧은 설명',
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_calendar_dates` (`start_date`, `end_date`)
);

-- 8. Refresh tokens persisted for logout/rotation
CREATE TABLE `refresh_token` (
    `id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL,
    `token_hash` VARCHAR(128) NOT NULL UNIQUE COMMENT 'SHA-256 hex of refresh token',
    `expires_at` DATETIME NOT NULL,
    `revoked` BOOLEAN NOT NULL DEFAULT FALSE,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_refresh_token_user` (`user_id`),
    CONSTRAINT `fk_refresh_token_user` FOREIGN KEY (`user_id`) REFERENCES `admin` (`admin_id`) ON DELETE CASCADE
);
