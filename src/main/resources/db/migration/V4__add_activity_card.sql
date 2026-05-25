CREATE TABLE `activity_card` (
    `card_id`    BIGINT       NOT NULL AUTO_INCREMENT,
    `main_id`    INT          NOT NULL DEFAULT 1,
    `title`      VARCHAR(100) NOT NULL,
    `content`    TEXT         NOT NULL,
    `card_order` INT          NOT NULL,
    `created_at` DATETIME(6)  NOT NULL,
    `updated_at` DATETIME(6)  NOT NULL,
    PRIMARY KEY (`card_id`),
    CONSTRAINT `fk_activity_card_main_page` FOREIGN KEY (`main_id`) REFERENCES `main_page` (`main_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO `activity_card` (`main_id`, `title`, `content`, `card_order`, `created_at`, `updated_at`)
VALUES (1, '스터디', '다양한 분야의 스터디를 운영합니다.', 1, NOW(6), NOW(6)),
       (1, '프로젝트', '팀 프로젝트를 통해 실력을 키웁니다.', 2, NOW(6), NOW(6)),
       (1, '세미나', '외부 연사를 초청해 세미나를 개최합니다.', 3, NOW(6), NOW(6)),
       (1, '네트워킹', '다양한 네트워킹 활동을 진행합니다.', 4, NOW(6), NOW(6));
