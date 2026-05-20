package org.one.domain.service;

import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.MainLogoUpdateRequestDto;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.repository.MainPageConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 메인 페이지 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminMainService {

	private final MainPageConfigRepository mainPageConfigRepository;

	/**
	 * 메인 페이지 로고 URL을 수정합니다.
	 *
	 * @param request 로고 수정 요청 DTO
	 * @return 수정된 로고 응답 DTO
	 */
	public MainLogoResponseDto update(MainLogoUpdateRequestDto request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.updateLogo(request.getLogoUrl());
		return MainLogoResponseDto.from(request.getLogoUrl());
	}
}
