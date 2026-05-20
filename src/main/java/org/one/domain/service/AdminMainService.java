package org.one.domain.service;

import org.one.domain.dto.request.MainLogoUpdateRequest;
import org.one.domain.dto.response.MainLogoResponse;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.repository.MainPageConfigRepository;
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
}
