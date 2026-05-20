package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 관리자 메인 로고 수정 응답 DTO입니다.
 */
@Schema(description = "로고 수정 응답")
@Getter
@Builder
@AllArgsConstructor
public class MainLogoResponseDto {

	@Schema(description = "로고 이미지 URL", example = "https://cdn.example.com/logo.png")
	private String logoUrl;

	public static MainLogoResponseDto from(String logoUrl) {
		return MainLogoResponseDto.builder()
				.logoUrl(logoUrl)
				.build();
	}
}
