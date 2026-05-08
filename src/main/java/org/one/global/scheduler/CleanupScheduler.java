package org.one.global.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupScheduler {

	private static final Logger log = LoggerFactory.getLogger(CleanupScheduler.class);

	@Scheduled(cron = "0 0 3 1 * *", zone = "Asia/Seoul")
	public void deleteOldApplications() {
		log.info("[CleanupScheduler] Scheduled cleanup placeholder - implement when domain ready");
	}

	@Scheduled(cron = "0 30 3 1 3 *", zone = "Asia/Seoul")
	public void incrementMemberGradeAndAge() {
		log.info("[CleanupScheduler] Scheduled member update placeholder - implement when domain ready");
	}
}
