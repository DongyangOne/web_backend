package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 관리자 메인 로고 수정 응답 DTO입니다.
 *
 * @param logoUrl 저장된 로고 URL
 */
@Schema(description = "로고 수정 응답")
public record MainLogoResponse(
		@Schema(description = "로고 이미지 URL", example = "https://cdn.example.com/logo.png")
		String logoUrl
) {}
