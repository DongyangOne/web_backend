package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 관리자 로그인 요청 DTO입니다.
 *
 * @param username 관리자 아이디
 * @param password 관리자 비밀번호
 */
@Schema(description = "관리자 로그인 요청")
public record LoginRequest(
		@Schema(description = "관리자 아이디", example = "admin")
		@NotBlank(message = "아이디를 입력해주세요.") String username,

		@Schema(description = "관리자 비밀번호", example = "admin1234")
		@NotBlank(message = "비밀번호를 입력해주세요.") String password
) {}
