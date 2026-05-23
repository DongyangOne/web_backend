ALTER TABLE `recruitment`
    DROP COLUMN `title`,
    ADD COLUMN `field` TEXT DEFAULT NULL AFTER `recruitment_id`;
