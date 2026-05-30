package org.one.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 관리자 인증 성공 시 반환하는 토큰 응답 DTO입니다.
 */
@Schema(description = "관리자 인증 토큰 응답")
@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDto {

	@Schema(description = "JWT Access Token")
	private String accessToken;

	@Schema(description = "Refresh Token")
	private String refreshToken;

	@Schema(description = "토큰 타입", example = "Bearer")
	private String tokenType;

	/**
	 * 토큰 타입을 Bearer로 고정한 응답 객체를 생성합니다.
	 *
	 * @param accessToken  JWT Access Token
	 * @param refreshToken Refresh Token
	 * @return 로그인 응답 DTO
	 */
	public static LoginResponseDto from(String accessToken, String refreshToken) {
		return LoginResponseDto.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.tokenType("Bearer")
				.build();
	}
}
