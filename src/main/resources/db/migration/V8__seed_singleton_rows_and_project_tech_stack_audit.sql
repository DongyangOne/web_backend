-- 이미 적용된 migration checksum을 변경하지 않고 운영/개발 DB에 누락된 singleton row와 감사 컬럼을 보강합니다.
-- main_page 기본 row는 V4 activity_card seed보다 먼저 필요하므로 V3.1에서 처리합니다.

INSERT INTO `recruitment` (
    `recruitment_id`,
    `field`,
    `target`,
    `recruitment_start`,
    `recruitment_end`,
    `interview_start`,
    `interview_end`,
    `notification_date`,
    `created_at`,
    `updated_at`
)
SELECT 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NOW(6), NOW(6)
WHERE NOT EXISTS (
    SELECT 1 FROM `recruitment` WHERE `recruitment_id` = 1
);

ALTER TABLE `project_tech_stack`
    ADD COLUMN `created_at` DATETIME(6) NULL,
    ADD COLUMN `updated_at` DATETIME(6) NULL;

UPDATE `project_tech_stack`
SET `created_at` = NOW(6)
WHERE `created_at` IS NULL;

UPDATE `project_tech_stack`
SET `updated_at` = `created_at`
WHERE `updated_at` IS NULL;

ALTER TABLE `project_tech_stack`
    MODIFY COLUMN `created_at` DATETIME(6) NOT NULL,
    MODIFY COLUMN `updated_at` DATETIME(6) NOT NULL;
