package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 메인 소개 문구 수정 요청 DTO입니다.
 */
@Schema(description = "소개 문구 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainIntroUpdateRequestDto {

	@Schema(description = "소개 문구", example = "ONE 동아리 소개 텍스트...")
	@Size(max = 1000, message = "소개 문구는 1000자 이하여야 합니다.")
	private String description;
}
