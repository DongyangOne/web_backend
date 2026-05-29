package org.one.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

	@Schema(description = "관리자 아이디", example = "admin")
	@NotBlank(message = "아이디를 입력해주세요.")
	private String username;

	@Schema(description = "관리자 비밀번호", example = "admin1234")
	@NotBlank(message = "비밀번호를 입력해주세요.")
	private String password;
}
