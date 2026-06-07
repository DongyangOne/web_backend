package org.one.global.scheduler;

import java.time.LocalDateTime;
import org.one.applicant.repository.ApplicantMemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 오래된 가입 신청 데이터를 주기적으로 정리하는 스케줄러입니다.
 */
@Component
public class CleanupScheduler {

	private static final Logger log = LoggerFactory.getLogger(CleanupScheduler.class);

	private final ApplicantMemberRepository applicantMemberRepository;

	/**
	 * 신청자 저장소를 주입받습니다.
	 *
	 * @param applicantMemberRepository 가입 신청자 저장소
	 */
	public CleanupScheduler(ApplicantMemberRepository applicantMemberRepository) {
		this.applicantMemberRepository = applicantMemberRepository;
	}

	/**
	 * 매월 1일 03시에 생성된 지 1년이 지난 가입 신청 데이터를 하드 딜리트합니다.
	 */
	@Scheduled(cron = "0 0 3 1 * *", zone = "Asia/Seoul")
	@Transactional
	public void deleteOldApplications() {
		LocalDateTime cutoff = LocalDateTime.now().minusYears(1);
		int deletedCount = applicantMemberRepository.deleteByCreatedAtBefore(cutoff);
		log.info("Deleted {} applicant applications created before {}", deletedCount, cutoff);
	}
}
