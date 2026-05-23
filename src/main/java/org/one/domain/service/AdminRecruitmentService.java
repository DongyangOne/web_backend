package org.one.domain.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.response.RecruitmentResponseDto;
import org.one.domain.entity.Recruitment;
import org.one.domain.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 모집 공고 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminRecruitmentService {

	private final RecruitmentRepository recruitmentRepository;

	/**
	 * 현재 모집 공고 정보를 조회합니다.
	 *
	 * @return 모집 공고 응답 DTO
	 */
	@Transactional(readOnly = true)
	public RecruitmentResponseDto findOne() {
		Recruitment recruitment = recruitmentRepository.findRecruitment();
		return RecruitmentResponseDto.from(recruitment, computeIsRecruiting(recruitment));
	}

	/**
	 * 현재 날짜를 기준으로 모집 중 여부를 계산합니다.
	 *
	 * @param recruitment 모집 공고 엔티티
	 * @return 모집 중 여부
	 */
	private boolean computeIsRecruiting(Recruitment recruitment) {
		LocalDate start = recruitment.getRecruitmentStart();
		LocalDate end = recruitment.getRecruitmentEnd();
		if (start == null || end == null) {
			return false;
		}
		LocalDate today = LocalDate.now();
		return !today.isBefore(start) && !today.isAfter(end);
	}
}
