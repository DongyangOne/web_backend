package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 프로젝트 사진 요청 DTO입니다.
 *
 * @param photoUrl 사진 URL
 * @param priority 노출 순서
 */
@Schema(description = "프로젝트 사진 요청")
public record PhotoRequest(
		@Schema(description = "사진 URL", example = "https://cdn.example.com/photo1.jpg")
		@NotBlank(message = "사진 URL을 입력해주세요.")
		@Size(max = 500, message = "사진 URL은 500자 이하여야 합니다.")
		String photoUrl,

		@Schema(description = "노출 순서 (오름차순)", example = "0")
		@NotNull(message = "사진 노출 순서를 입력해주세요.")
		Integer priority
) {}
