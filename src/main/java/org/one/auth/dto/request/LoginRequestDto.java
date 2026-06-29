package org.one.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 로그인 요청 DTO입니다.
 */
@Schema(description = "관리자 로그인 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

	@Schema(description = "관리자 아이디", example = "one")
	@NotBlank(message = "아이디를 입력해주세요.")
	@Size(min = 3, max = 15, message = "아이디는 3자 이상 15자 이하여야 합니다.")
	@Pattern(regexp = "^[a-z0-9]+$", message = "아이디는 영어 소문자와 숫자만 사용할 수 있습니다.")
	private String username;

	@Schema(description = "관리자 비밀번호", example = "one2026*")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하여야 합니다.")
	@Pattern(regexp = "^[a-z0-9!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>?/\\\\|`~]+$", message = "비밀번호는 영어 소문자, 숫자, 특수문자만 사용할 수 있습니다.")
	private String password;
}
