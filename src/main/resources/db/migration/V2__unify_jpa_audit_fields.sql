-- BaseEntity(created_at, updated_at) 기준으로 감사 필드를 통일합니다.

-- admin: updated_at 추가 및 default 의존 제거
ALTER TABLE `admin`
    ADD COLUMN `updated_at` TIMESTAMP NULL AFTER `created_at`;
UPDATE `admin`
SET `updated_at` = `created_at`
WHERE `updated_at` IS NULL;
ALTER TABLE `admin`
    MODIFY COLUMN `created_at` TIMESTAMP NOT NULL,
    MODIFY COLUMN `updated_at` TIMESTAMP NOT NULL;

-- main_page: created_at 추가 및 default/on update 의존 제거
ALTER TABLE `main_page`
    ADD COLUMN `created_at` TIMESTAMP NULL AFTER `main_id`;
UPDATE `main_page`
SET `created_at` = `updated_at`
WHERE `created_at` IS NULL;
ALTER TABLE `main_page`
    MODIFY COLUMN `created_at` TIMESTAMP NOT NULL,
    MODIFY COLUMN `updated_at` TIMESTAMP NOT NULL;

-- project_event: updated_at 추가
ALTER TABLE `project_event`
    ADD COLUMN `updated_at` TIMESTAMP NULL AFTER `created_at`;
UPDATE `project_event`
SET `updated_at` = `created_at`
WHERE `updated_at` IS NULL;
ALTER TABLE `project_event`
    MODIFY COLUMN `created_at` TIMESTAMP NOT NULL,
    MODIFY COLUMN `updated_at` TIMESTAMP NOT NULL;

-- project_photo: updated_at 추가
ALTER TABLE `project_photo`
    ADD COLUMN `updated_at` TIMESTAMP NULL AFTER `created_at`;
UPDATE `project_photo`
SET `updated_at` = `created_at`
WHERE `updated_at` IS NULL;
ALTER TABLE `project_photo`
    MODIFY COLUMN `created_at` TIMESTAMP NOT NULL,
    MODIFY COLUMN `updated_at` TIMESTAMP NOT NULL;

-- applicant_member: updated_at 추가
ALTER TABLE `applicant_member`
    ADD COLUMN `updated_at` TIMESTAMP NULL AFTER `created_at`;
UPDATE `applicant_member`
SET `updated_at` = `created_at`
WHERE `updated_at` IS NULL;
ALTER TABLE `applicant_member`
    MODIFY COLUMN `created_at` TIMESTAMP NOT NULL,
    MODIFY COLUMN `updated_at` TIMESTAMP NOT NULL;

-- member: registered_at -> created_at로 통일하고 updated_at 추가
ALTER TABLE `member`
    CHANGE COLUMN `registered_at` `created_at` TIMESTAMP NOT NULL;
ALTER TABLE `member`
    ADD COLUMN `updated_at` TIMESTAMP NULL AFTER `created_at`;
UPDATE `member`
SET `updated_at` = `created_at`
WHERE `updated_at` IS NULL;
ALTER TABLE `member`
    MODIFY COLUMN `created_at` TIMESTAMP NOT NULL,
    MODIFY COLUMN `updated_at` TIMESTAMP NOT NULL;

-- calendar_schedule: updated_at 추가
ALTER TABLE `calendar_schedule`
    ADD COLUMN `updated_at` TIMESTAMP NULL AFTER `created_at`;
UPDATE `calendar_schedule`
SET `updated_at` = `created_at`
WHERE `updated_at` IS NULL;
ALTER TABLE `calendar_schedule`
    MODIFY COLUMN `created_at` TIMESTAMP NOT NULL,
    MODIFY COLUMN `updated_at` TIMESTAMP NOT NULL;

-- refresh_token: default/on update 의존 제거
ALTER TABLE `refresh_token`
    MODIFY COLUMN `created_at` TIMESTAMP NOT NULL,
    MODIFY COLUMN `updated_at` TIMESTAMP NOT NULL;
