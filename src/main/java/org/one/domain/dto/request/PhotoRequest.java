package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 사진 추가 요청 DTO입니다.
 *
 * @param photoUrl 사진 URL
 * @param priority 노출 순서
 */
@Schema(description = "사진 추가 요청")
public record PhotoRequest(
		@Schema(description = "사진 URL", example = "https://cdn.example.com/photo.jpg")
		@NotBlank(message = "사진 URL을 입력해주세요.")
		String photoUrl,

		@Schema(description = "노출 순서", example = "0")
		@NotNull(message = "노출 순서를 입력해주세요.")
		Integer priority
) {}
