-- V4 activity_card 초기 데이터가 main_page(1)을 참조하므로 V4 이전에 기본 row를 보장합니다.
INSERT INTO `main_page` (`main_id`, `logo_url`, `description`, `created_at`, `updated_at`)
SELECT 1, NULL, NULL, NOW(6), NOW(6)
WHERE NOT EXISTS (
    SELECT 1 FROM `main_page` WHERE `main_id` = 1
);
