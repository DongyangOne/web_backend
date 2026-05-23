package org.one.domain.service;

import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ActivityCardUpdateRequestDto;
import org.one.domain.dto.request.MainLogoUpdateRequestDto;
import org.one.domain.dto.response.ActivityCardResponseDto;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.entity.ActivityCard;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.repository.ActivityCardRepository;
import org.one.domain.repository.MainPageConfigRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
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
	private final ActivityCardRepository activityCardRepository;

	/**
	 * 메인 페이지 로고 URL을 수정합니다.
	 *
	 * @param request 로고 수정 요청 DTO
	 * @return 수정된 로고 응답 DTO
	 */
	public MainLogoResponseDto updateLogo(MainLogoUpdateRequestDto request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		config.updateLogo(request.getLogoUrl());
		return MainLogoResponseDto.from(config.getLogoUrl());
	}

	/**
	 * 주요활동 카드를 수정합니다.
	 *
	 * @param cardId 수정할 카드 ID
	 * @param request 수정 요청 DTO
	 * @return 수정된 카드 응답 DTO
	 */
	public ActivityCardResponseDto updateActivityCard(Long cardId, ActivityCardUpdateRequestDto request) {
		ActivityCard card = activityCardRepository.findById(cardId)
				.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
		card.update(request.getTitle(), request.getContent());
		return ActivityCardResponseDto.from(card);
	}
}
