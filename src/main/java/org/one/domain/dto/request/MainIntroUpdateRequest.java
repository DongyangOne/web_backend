package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 관리자 메인 소개 문구 수정 요청 DTO입니다.
 *
 * @param description 소개 문구
 */
@Schema(description = "관리자 메인 소개 수정 요청")
public record MainIntroUpdateRequest(
		@Schema(description = "소개 문구", example = "ONE은 웹/앱 등 다양한 프로젝트를 수행합니다.")
		@NotBlank(message = "소개 문구를 입력해주세요.")
		String description
) {}
