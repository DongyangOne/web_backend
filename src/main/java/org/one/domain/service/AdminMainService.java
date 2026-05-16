package org.one.domain.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.one.domain.dto.request.MainIntroUpdateRequest;
import org.one.domain.dto.request.MainLogoUpdateRequest;
import org.one.domain.dto.request.MainRecruitmentUpdateRequest;
import org.one.domain.dto.response.AdminMainSummaryResponse;
import org.one.domain.dto.response.MemberStatusCountResponse;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.enums.MemberStatus;
import org.one.domain.repository.MainPageConfigRepository;
import org.one.domain.repository.MemberRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 메인 페이지(로고/소개/모집기간/부원통계) 조회 및 수정을 담당합니다.
 */
@Service
@Transactional
public class AdminMainService {

	private final MainPageConfigRepository mainPageConfigRepository;
	private final MemberRepository memberRepository;

	/**
	 * 관리자 메인 서비스에 필요한 저장소를 주입받습니다.
	 *
	 * @param mainPageConfigRepository 메인 설정 저장소
	 * @param memberRepository 부원 저장소
	 */
	public AdminMainService(MainPageConfigRepository mainPageConfigRepository,
			MemberRepository memberRepository) {
		this.mainPageConfigRepository = mainPageConfigRepository;
		this.memberRepository = memberRepository;
	}

	/**
	 * 관리자 메인 요약 정보를 조회합니다.
	 *
	 * @return 메인 요약 정보
	 */
	@Transactional(readOnly = true)
	public AdminMainSummaryResponse getSummary() {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		List<MemberStatusCountResponse> counts = buildMemberStatusCounts();

		return new AdminMainSummaryResponse(
				config.getLogoUrl(),
				config.getDescription(),
				computeIsRecruiting(config),
				config.getRecruitmentStart(),
				config.getRecruitmentEnd(),
				memberRepository.count(),
				counts,
				config.getUpdatedAt()
		);
	}

	/**
	 * 로고 URL을 수정합니다.
	 *
	 * @param request 로고 수정 요청
	 * @return 수정 후 메인 요약 정보
	 */
	public AdminMainSummaryResponse updateLogo(MainLogoUpdateRequest request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.update(
				request.logoUrl(),
				config.getDescription(),
				config.getRecruitmentStart(),
				config.getRecruitmentEnd()
		);
		return getSummary();
	}

	/**
	 * 소개 문구를 수정합니다.
	 *
	 * @param request 소개 수정 요청
	 * @return 수정 후 메인 요약 정보
	 */
	public AdminMainSummaryResponse updateIntro(MainIntroUpdateRequest request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.update(
				config.getLogoUrl(),
				request.description(),
				config.getRecruitmentStart(),
				config.getRecruitmentEnd()
		);
		return getSummary();
	}

	/**
	 * 모집 기간을 수정합니다.
	 *
	 * @param request 모집기간 수정 요청
	 * @return 수정 후 메인 요약 정보
	 */
	public AdminMainSummaryResponse updateRecruitment(MainRecruitmentUpdateRequest request) {
		if (request.recruitmentStart().isAfter(request.recruitmentEnd())) {
			throw new BusinessException(ErrorCode.INVALID_INPUT);
		}

		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.update(
				config.getLogoUrl(),
				config.getDescription(),
				request.recruitmentStart(),
				request.recruitmentEnd()
		);
		return getSummary();
	}

	/**
	 * 현재 날짜가 모집 기간 범위 내에 있으면 true를 반환합니다.
	 *
	 * @param config 메인 페이지 설정
	 * @return 모집 중 여부
	 */
	private boolean computeIsRecruiting(MainPageConfig config) {
		LocalDate start = config.getRecruitmentStart();
		LocalDate end = config.getRecruitmentEnd();
		if (start == null || end == null) {
			return false;
		}
		LocalDate today = LocalDate.now();
		return !today.isBefore(start) && !today.isAfter(end);
	}

	private List<MemberStatusCountResponse> buildMemberStatusCounts() {
		Map<MemberStatus, Long> countMap = new EnumMap<>(MemberStatus.class);
		for (MemberStatus status : MemberStatus.values()) {
			countMap.put(status, 0L);
		}

		for (Object[] row : memberRepository.countGroupByStatus()) {
			MemberStatus status = (MemberStatus) row[0];
			Long count = (Long) row[1];
			countMap.put(status, count);
		}

		return Arrays.stream(MemberStatus.values())
				.map(status -> new MemberStatusCountResponse(status, countMap.get(status)))
				.toList();
	}
}
