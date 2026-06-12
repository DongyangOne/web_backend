-- Refresh Token을 사용자당 1개 row로 제한해 단일 세션 정책을 보장합니다.

DELETE rt1
FROM `refresh_token` rt1
JOIN `refresh_token` rt2
  ON rt1.`user_id` = rt2.`user_id`
 AND (
		rt1.`updated_at` < rt2.`updated_at`
		OR (rt1.`updated_at` = rt2.`updated_at` AND rt1.`id` < rt2.`id`)
	 );

ALTER TABLE `refresh_token`
    DROP INDEX `idx_refresh_token_user`,
    ADD UNIQUE KEY `uk_refresh_token_user_id` (`user_id`);
