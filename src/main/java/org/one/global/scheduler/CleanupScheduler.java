package org.one.global.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 도메인 정리 작업이 준비되기 전까지 스케줄 실행 지점을 보존하는 placeholder 스케줄러입니다.
 */
@Component
public class CleanupScheduler {

	private static final Logger log = LoggerFactory.getLogger(CleanupScheduler.class);

	/**
	 * 매월 1일 03시에 오래된 가입 신청 정리 작업을 실행할 자리입니다.
	 */
	@Scheduled(cron = "0 0 3 1 * *", zone = "Asia/Seoul")
	public void deleteOldApplications() {
		log.info("[CleanupScheduler] Scheduled cleanup placeholder - implement when domain ready");
	}

	/**
	 * 매년 3월 1일 03시 30분에 부원 학년/나이 갱신 작업을 실행할 자리입니다.
	 */
	@Scheduled(cron = "0 30 3 1 3 *", zone = "Asia/Seoul")
	public void incrementMemberGradeAndAge() {
		log.info("[CleanupScheduler] Scheduled member update placeholder - implement when domain ready");
	}
}
