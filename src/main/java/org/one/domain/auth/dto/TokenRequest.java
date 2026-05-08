package org.one.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record TokenRequest(
		@Schema(description = "refresh token", example = "_t4nIn9hcIOg9UBhNDfvgLO8RUuO_NE3s7eQNM5RMxA")
		@NotBlank(message = "리프레시 토큰을 입력해주세요.") String refreshToken
) {
}
