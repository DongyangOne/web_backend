package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * 사진 순서 변경 항목 DTO입니다.
 *
 * @param photoId 사진 ID
 * @param priority 변경할 노출 순서
 */
@Schema(description = "사진 순서 변경 항목")
public record PhotoOrderItem(
		@Schema(description = "사진 ID", example = "1")
		@NotNull(message = "사진 ID를 입력해주세요.")
		Long photoId,

		@Schema(description = "노출 순서", example = "0")
		@NotNull(message = "노출 순서를 입력해주세요.")
		Integer priority
) {}
