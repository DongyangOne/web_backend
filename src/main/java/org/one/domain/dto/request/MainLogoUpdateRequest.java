package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

/**
 * 관리자 메인 로고 수정 요청 DTO입니다.
 *
 * @param logoUrl 로고 이미지 URL (최대 500자, null 허용 시 로고 제거)
 */
@Schema(description = "로고 수정 요청")
public record MainLogoUpdateRequest(
		@Schema(description = "로고 이미지 URL", example = "https://cdn.example.com/logo.png")
		@Size(max = 500, message = "로고 URL은 500자 이하여야 합니다.")
		String logoUrl
) {}
