CREATE TABLE `recruitment`
(
    `recruitment_id`    INT          NOT NULL DEFAULT 1 PRIMARY KEY,
    `title`             VARCHAR(200)          DEFAULT NULL,
    `target`            VARCHAR(255)          DEFAULT NULL,
    `recruitment_start` DATE                  DEFAULT NULL,
    `recruitment_end`   DATE                  DEFAULT NULL,
    `interview_start`   DATE                  DEFAULT NULL,
    `interview_end`     DATE                  DEFAULT NULL,
    `notification_date` DATE                  DEFAULT NULL,
    `created_at`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at`        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE `main_page`
    DROP COLUMN `recruitment_start`,
    DROP COLUMN `recruitment_end`;
