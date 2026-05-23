-- 메인 페이지 소개 문구(description) 제거, 주요활동 카드 테이블 추가
ALTER TABLE `main_page` DROP COLUMN `description`;

CREATE TABLE `activity_card` (
    `card_id`    BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `main_id`    INT       NOT NULL DEFAULT 1,
    `title`      TEXT               DEFAULT NULL,
    `content`    TEXT               DEFAULT NULL,
    `card_order` INT       NOT NULL,
    `updated_at` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `fk_activity_main` FOREIGN KEY (`main_id`) REFERENCES `main_page` (`main_id`)
);

INSERT INTO `activity_card` (`main_id`, `card_order`) VALUES (1, 1), (1, 2), (1, 3), (1, 4);
