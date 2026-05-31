package org.one.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.auth.dto.request.LoginRequestDto;
import org.one.auth.dto.request.TokenRequestDto;
import org.one.auth.dto.response.LoginResponseDto;
import org.one.auth.service.AdminAuthService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 인증 API 요청을 받고 서비스 계층으로 위임합니다.
 */
@Tag(name = "Auth", description = "관리자 인증")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AdminAuthService adminAuthService;

	/**
	 * 관리자 아이디와 비밀번호로 Access Token과 Refresh Token을 발급합니다.
	 *
	 * @param request 로그인 요청 정보
	 * @return 토큰 발급 응답
	 */
	@Operation(summary = "관리자 로그인", description = "관리자 계정 정보를 검증하고 JWT Access Token과 Refresh Token을 발급합니다.")
	@SecurityRequirements
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.INVALID_CREDENTIALS, ErrorCode.INTERNAL_SERVER_ERROR})
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponseDto>> login(
			@RequestBody @Valid LoginRequestDto request) {
		LoginResponseDto tokens = adminAuthService.login(request);
		return ResponseEntity.ok(ApiResponse.success(tokens));
	}

	/**
	 * Refresh Token으로 새로운 Access Token과 Refresh Token을 발급합니다.
	 *
	 * @param request Refresh Token 요청 정보
	 * @return 재발급된 토큰 응답
	 */
	@Operation(summary = "Access Token 재발급", description = "Refresh Token으로 새로운 Access Token을 발급합니다.")
	@SecurityRequirements
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.INVALID_TOKEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<LoginResponseDto>> refresh(
			@RequestBody @Valid TokenRequestDto request) {
		LoginResponseDto tokens = adminAuthService.refresh(request.getRefreshToken());
		return ResponseEntity.ok(ApiResponse.success(tokens));
	}

	/**
	 * Refresh Token을 폐기해 로그아웃 처리합니다.
	 *
	 * @param request Refresh Token 요청 정보
	 * @return 로그아웃 완료 응답
	 */
	@Operation(summary = "로그아웃", description = "Refresh Token을 폐기합니다.")
	@SecurityRequirements
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.INTERNAL_SERVER_ERROR})
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(
			@RequestBody @Valid TokenRequestDto request) {
		adminAuthService.logout(request.getRefreshToken());
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
