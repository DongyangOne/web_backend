package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 관리자 인증 성공 시 반환하는 토큰 응답 DTO입니다.
 *
 * @param accessToken API 인증에 사용할 JWT Access Token
 * @param refreshToken Access Token 재발급에 사용할 Refresh Token
 * @param tokenType Authorization 헤더에 사용할 토큰 타입
 */
@Schema(description = "관리자 인증 토큰 응답")
public record LoginResponse(

		@Schema(description = "JWT Access Token")
		String accessToken,

		@Schema(description = "Refresh Token")
		String refreshToken,

		@Schema(description = "토큰 타입", example = "Bearer")
		String tokenType
) {
	/**
	 * 토큰 타입을 Bearer로 고정한 응답 객체를 생성합니다.
	 *
	 * @param accessToken JWT Access Token
	 * @param refreshToken Refresh Token
	 * @return 로그인 응답 DTO
	 */
	public static LoginResponse of(String accessToken, String refreshToken) {
		return new LoginResponse(accessToken, refreshToken, "Bearer");
	}
}
