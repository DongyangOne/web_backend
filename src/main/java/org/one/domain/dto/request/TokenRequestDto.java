package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Refresh Token을 전달하는 요청 DTO입니다.
 */
@Schema(description = "Refresh Token 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {

	@Schema(description = "리프레시 토큰", example = "_t4nIn9hcIOg9UBhNDfvgLO8RUuO_NE3s7eQNM5RMxA")
	@NotBlank(message = "리프레시 토큰을 입력해주세요.")
	private String refreshToken;
}
