package org.one.domain.service;

import lombok.RequiredArgsConstructor;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.repository.MainPageConfigRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.global.service.MinioService;
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
	private final MinioService minioService;

	/**
	 * 메인 페이지 로고 URL을 수정합니다.
	 * 클라이언트가 Presigned URL로 MinIO에 직접 업로드한 뒤, objectKey를 전달합니다.
	 * 기존 로고가 있으면 MinIO에서 삭제합니다.
	 *
	 * @param objectKey Presigned URL 발급 시 받은 객체 키 (예: "logo/uuid")
	 * @return 수정된 로고 응답 DTO
	 */
	public MainLogoResponseDto update(String objectKey) {
		validateObjectKey(objectKey);

		MainPageConfig config = mainPageConfigRepository.getConfig();

		if (config.getLogoUrl() != null) {
			minioService.deleteFile(minioService.extractObjectKey(config.getLogoUrl()));
		}

		String url = minioService.getObjectUrl(objectKey);
		config.updateLogo(url);
		return MainLogoResponseDto.from(url);
	}

	/**
	 * objectKey가 로고 경로("logo/")로 시작하는지 검증합니다.
	 *
	 * @param objectKey 검증할 객체 키
	 */
	private void validateObjectKey(String objectKey) {
		if (!objectKey.startsWith("logo/")) {
			throw new BusinessException(ErrorCode.INVALID_INPUT);
		}
	}
}
