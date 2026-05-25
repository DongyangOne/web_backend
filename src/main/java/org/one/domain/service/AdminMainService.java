package org.one.domain.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 메인 페이지 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminMainService {

	private static final Logger log = LoggerFactory.getLogger(AdminMainService.class);

	private final MainPageConfigRepository mainPageConfigRepository;
	private final ActivityCardRepository activityCardRepository;
	private final MinioService minioService;

	/**
	 * 메인 페이지 로고 URL을 수정합니다.
	 * 클라이언트가 Presigned URL로 MinIO에 직접 업로드한 뒤, objectKey를 전달합니다.
	 * DB를 먼저 갱신한 뒤 기존 로고를 MinIO에서 삭제합니다.
	 * MinIO 삭제 실패 시 예외를 던지지 않고 경고 로그를 남깁니다.
	 *
	 * @param objectKey Presigned URL 발급 시 받은 객체 키 (예: "logo/uuid")
	 * @return 수정된 로고 응답 DTO
	 */
	public MainLogoResponseDto update(String objectKey) {
		validateObjectKey(objectKey);

		MainPageConfig config = mainPageConfigRepository.getConfig();
		String oldLogoUrl = config.getLogoUrl();

		String url = minioService.getObjectUrl(objectKey);
		config.updateLogo(url);

		if (oldLogoUrl != null) {
			try {
				minioService.deleteFile(minioService.extractObjectKey(oldLogoUrl));
			} catch (Exception e) {
				log.warn("[AdminMainService] 기존 로고 MinIO 삭제 실패: {}", oldLogoUrl, e);
			}
		}

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

	/**
	 * 주요활동 카드 내용을 초기화합니다.
	 *
	 * @param cardId 초기화할 카드 ID
	 */
	public void clearActivityCard(Long cardId) {
		ActivityCard card = activityCardRepository.findById(cardId)
				.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
		card.clear();
	}

	/**
	 * objectKey가 로고 경로("logo/")로 시작하는지 검증합니다.
	 *
	 * @param objectKey 검증할 객체 키
	 */
	private void validateObjectKey(String objectKey) {
		if (!objectKey.startsWith("logo/")) {
			throw new BusinessException(ErrorCode.INVALID_OBJECT_KEY);
		}
	}
}
