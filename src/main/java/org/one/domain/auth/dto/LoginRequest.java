package org.one.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginRequest(
		@Schema(description = "관리자 아이디", example = "admin")
		@NotBlank(message = "아이디를 입력해주세요.") String username,
		@Schema(description = "관리자 비밀번호", example = "admin1234")
		@NotBlank(message = "비밀번호를 입력해주세요.") String password
) {}
