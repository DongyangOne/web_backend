package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 관리자 메인 소개 문구 수정 응답 DTO입니다.
 */
@Schema(description = "소개 문구 수정 응답")
@Getter
@Builder
@AllArgsConstructor
public class MainIntroResponseDto {

	@Schema(description = "소개 문구", example = "ONE 동아리 소개 텍스트...")
	private String description;

	public static MainIntroResponseDto from(String description) {
		return MainIntroResponseDto.builder()
				.description(description)
				.build();
	}
}
