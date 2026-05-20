package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.MainLogoUpdateRequestDto;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.service.AdminMainService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 메인 페이지 API 요청을 받고 서비스 계층으로 위임합니다.
 */
@Tag(name = "Admin - Main", description = "관리자 메인 페이지")
@RestController
@RequestMapping("/api/v1/admin/main")
@RequiredArgsConstructor
public class AdminMainController {

	private final AdminMainService adminMainService;

	/**
	 * 메인 페이지 로고 URL을 수정합니다.
	 *
	 * @param request 로고 수정 요청 DTO
	 * @return 수정된 로고 응답
	 */
	@Operation(summary = "관리자 메인 로고 수정", description = "로고 URL을 즉시 저장합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping("/logo")
	public ResponseEntity<ApiResponse<MainLogoResponseDto>> update(
			@RequestBody @Valid MainLogoUpdateRequestDto request) {
		return ResponseEntity.ok(ApiResponse.success(adminMainService.update(request)));
	}
}
