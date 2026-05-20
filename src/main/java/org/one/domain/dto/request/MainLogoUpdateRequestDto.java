package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 메인 로고 수정 요청 DTO입니다.
 */
@Schema(description = "로고 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainLogoUpdateRequestDto {

	@Schema(description = "로고 이미지 URL", example = "https://cdn.example.com/logo.png")
	@Size(max = 500, message = "로고 URL은 500자 이하여야 합니다.")
	private String logoUrl;
}
