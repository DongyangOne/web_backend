-- project_event 테이블에 년도, 수상, 기간 컬럼 추가 및 project_name 길이 제한 해제
ALTER TABLE `project_event`
    MODIFY COLUMN `project_name` TEXT         NOT NULL,
    ADD COLUMN `year`            TEXT                  DEFAULT NULL AFTER `main_id`,
    ADD COLUMN `award`           TEXT                  DEFAULT NULL AFTER `project_name`,
    ADD COLUMN `activity`        TEXT                  DEFAULT NULL AFTER `award`,
    ADD COLUMN `start_date`      DATE                  DEFAULT NULL AFTER `activity`,
    ADD COLUMN `end_date`        DATE                  DEFAULT NULL AFTER `start_date`;

-- 프로젝트 기술 스택 테이블 추가
CREATE TABLE `project_tech_stack` (
    `tech_stack_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `project_id`    BIGINT NOT NULL,
    `name`          TEXT   NOT NULL,
    CONSTRAINT `fk_tech_stack_project` FOREIGN KEY (`project_id`) REFERENCES `project_event` (`project_id`) ON DELETE CASCADE
);
