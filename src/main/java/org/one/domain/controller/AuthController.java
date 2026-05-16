package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.one.domain.dto.request.LoginRequest;
import org.one.domain.dto.request.TokenRequest;
import org.one.domain.dto.response.LoginResponse;
import org.one.domain.service.AdminAuthService;
import org.one.global.dto.ApiResponse;
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
@RequestMapping("/auth")
public class AuthController {

	private final AdminAuthService adminAuthService;

	/**
	 * 관리자 인증 서비스를 주입받습니다.
	 *
	 * @param adminAuthService 관리자 인증 서비스
	 */
	public AuthController(AdminAuthService adminAuthService) {
		this.adminAuthService = adminAuthService;
	}

	/**
	 * 관리자 아이디와 비밀번호로 Access Token과 Refresh Token을 발급합니다.
	 *
	 * @param request 로그인 요청 정보
	 * @return 토큰 발급 응답
	 */
	@SecurityRequirements
	@Operation(summary = "관리자 로그인", description = "관리자 계정 정보를 검증하고 JWT Access Token과 Refresh Token을 발급합니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "관리자 로그인 정보",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"username\":\"admin\",\"password\":\"admin1234\"}")))
			@RequestBody @Valid LoginRequest request) {
		LoginResponse tokens = adminAuthService.login(request);
		return ResponseEntity.ok(ApiResponse.success(tokens));
	}

	/**
	 * Refresh Token으로 새로운 Access Token과 Refresh Token을 발급합니다.
	 *
	 * @param request Refresh Token 요청 정보
	 * @return 재발급된 토큰 응답
	 */
	@SecurityRequirements
	@Operation(summary = "Access Token 재발급", description = "Refresh Token으로 새로운 Access Token을 발급합니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<LoginResponse>> refresh(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "재발급에 사용할 Refresh Token",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"refreshToken\":\"aqMScC7hBmyl...\"}")))
			@RequestBody @Valid TokenRequest request) {
		LoginResponse tokens = adminAuthService.refresh(request.refreshToken());
		return ResponseEntity.ok(ApiResponse.success(tokens));
	}

	/**
	 * Refresh Token을 폐기해 로그아웃 처리합니다.
	 *
	 * @param request Refresh Token 요청 정보
	 * @return 로그아웃 완료 응답
	 */
	@SecurityRequirements
	@Operation(summary = "로그아웃", description = "Refresh Token을 폐기합니다.")
	@io.swagger.v3.oas.annotations.responses.ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그아웃 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "폐기할 Refresh Token",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"refreshToken\":\"aqMScC7hBmyl...\"}")))
			@RequestBody @Valid TokenRequest request) {
		adminAuthService.logout(request.refreshToken());
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
