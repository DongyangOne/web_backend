package org.one.domain.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.one.domain.auth.dto.LoginRequest;
import org.one.domain.auth.dto.LoginResponse;
import org.one.domain.auth.dto.TokenRequest;
import org.one.domain.auth.service.AdminAuthService;
import org.one.global.apiPayload.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "관리자 인증")
@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AdminAuthService adminAuthService;

	public AuthController(AdminAuthService adminAuthService) {
		this.adminAuthService = adminAuthService;
	}

	@Operation(summary = "관리자 로그인")
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {
		LoginResponse tokens = adminAuthService.login(request);
		return ResponseEntity.ok(ApiResponse.success(tokens));
	}

	@Operation(summary = "Refresh token 으로 access 재발급")
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<LoginResponse>> refresh(@RequestBody @Valid TokenRequest request) {
		LoginResponse tokens = adminAuthService.refresh(request.refreshToken());
		return ResponseEntity.ok(ApiResponse.success(tokens));
	}

	@Operation(summary = "로그아웃(리프레시 토큰 폐기)")
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(@RequestBody @Valid TokenRequest request) {
		adminAuthService.logout(request.refreshToken());
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
