package org.one.domain.service;

import java.time.LocalDate;
import org.one.domain.dto.request.MainIntroUpdateRequest;
import org.one.domain.dto.request.MainLogoUpdateRequest;
import org.one.domain.dto.request.MainRecruitmentUpdateRequest;
import org.one.domain.dto.response.MainIntroResponse;
import org.one.domain.dto.response.MainLogoResponse;
import org.one.domain.dto.response.MainRecruitmentResponse;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.repository.MainPageConfigRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 메인 페이지(로고/소개/모집기간) 수정을 담당합니다.
 */
@Service
@Transactional
public class AdminMainService {

	private final MainPageConfigRepository mainPageConfigRepository;

	/**
	 * 관리자 메인 서비스에 필요한 저장소를 주입받습니다.
	 *
	 * @param mainPageConfigRepository 메인 설정 저장소
	 */
	public AdminMainService(MainPageConfigRepository mainPageConfigRepository) {
		this.mainPageConfigRepository = mainPageConfigRepository;
	}

	/**
	 * 로고 URL을 수정합니다.
	 *
	 * @param request 로고 수정 요청
	 * @return 저장된 로고 URL
	 */
	public MainLogoResponse updateLogo(MainLogoUpdateRequest request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.update(
				request.logoUrl(),
				config.getDescription(),
				config.getRecruitmentStart(),
				config.getRecruitmentEnd()
		);
		return new MainLogoResponse(request.logoUrl());
	}

	/**
	 * 소개 문구를 수정합니다.
	 *
	 * @param request 소개 수정 요청
	 * @return 저장된 소개 문구
	 */
	public MainIntroResponse updateIntro(MainIntroUpdateRequest request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.update(
				config.getLogoUrl(),
				request.description(),
				config.getRecruitmentStart(),
				config.getRecruitmentEnd()
		);
		return new MainIntroResponse(request.description());
	}

	/**
	 * 모집 기간을 수정합니다.
	 *
	 * @param request 모집기간 수정 요청
	 * @return 저장된 모집 기간 및 모집 여부
	 */
	public MainRecruitmentResponse updateRecruitment(MainRecruitmentUpdateRequest request) {
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
		return new MainRecruitmentResponse(
				computeIsRecruiting(config),
				request.recruitmentStart(),
				request.recruitmentEnd()
		);
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
}
