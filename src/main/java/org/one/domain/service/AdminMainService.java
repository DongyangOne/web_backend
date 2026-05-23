package org.one.domain.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ActivityCardUpdateRequestDto;
import org.one.domain.dto.response.ActivityCardResponseDto;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.entity.ActivityCard;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.repository.ActivityCardRepository;
import org.one.domain.repository.MainPageConfigRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.global.service.MinioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 관리자 메인 페이지 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminMainService {

	private final MainPageConfigRepository mainPageConfigRepository;
	private final ActivityCardRepository activityCardRepository;
	private final MinioService minioService;

	/**
	 * 메인 페이지 로고 이미지를 MinIO에 업로드하고 URL을 저장합니다.
	 * 기존 로고가 있으면 MinIO에서 삭제합니다.
	 *
	 * @param logo 로고 이미지 파일 (이미지만 허용)
	 * @return 수정된 로고 응답 DTO
	 */
	public MainLogoResponseDto update(MultipartFile logo) {
		String contentType = logo.getContentType();
		if (contentType == null || !contentType.startsWith("image/")) {
			throw new BusinessException(ErrorCode.INVALID_INPUT);
		}

		MainPageConfig config = mainPageConfigRepository.getConfig();

		if (config.getLogoUrl() != null) {
			minioService.deleteFile(minioService.extractObjectKey(config.getLogoUrl()));
		}

		String objectKey = "logo/" + UUID.randomUUID();
		String url = minioService.uploadFile(logo, objectKey);
		config.updateLogo(url);
		return MainLogoResponseDto.from(url);
	}

	/**
	 * 주요활동 카드 내용을 수정합니다.
	 *
	 * @param cardId 수정할 카드 ID
	 * @param request 카드 수정 요청 DTO
	 * @return 수정된 카드 응답 DTO
	 */
	public ActivityCardResponseDto updateActivityCard(Long cardId, ActivityCardUpdateRequestDto request) {
		ActivityCard card = activityCardRepository.findById(cardId)
				.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
		card.update(request.getTitle(), request.getContent());
		return ActivityCardResponseDto.from(card);
	}
}
