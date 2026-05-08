package org.one.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(String accessToken, String refreshToken, String tokenType) {

	@Schema(description = "JWT access token")
	public String accessToken() {
		return accessToken;
	}

	@Schema(description = "refresh token")
	public String refreshToken() {
		return refreshToken;
	}

	@Schema(description = "token type", example = "Bearer")
	public String tokenType() {
		return tokenType;
	}

	public static LoginResponse of(String accessToken, String refreshToken) {
		return new LoginResponse(accessToken, refreshToken, "Bearer");
	}
}
