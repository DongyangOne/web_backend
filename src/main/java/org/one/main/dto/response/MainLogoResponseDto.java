package org.one.main.dto.response;

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

	/**
	 * 로고 URL 문자열에서 응답 DTO를 생성합니다.
	 *
	 * @param logoUrl 로고 이미지 URL
	 * @return 로고 수정 응답 DTO
	 */
	public static MainLogoResponseDto from(String logoUrl) {
		return MainLogoResponseDto.builder()
				.logoUrl(logoUrl)
				.build();
	}
}
