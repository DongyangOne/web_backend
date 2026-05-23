package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ActivityCardUpdateRequestDto;
import org.one.domain.dto.request.MainLogoUpdateRequestDto;
import org.one.domain.dto.response.ActivityCardResponseDto;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.service.AdminMainService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<ApiResponse<MainLogoResponseDto>> updateLogo(
			@RequestBody @Valid MainLogoUpdateRequestDto request) {
		return ResponseEntity.ok(ApiResponse.success(adminMainService.updateLogo(request)));
	}

	/**
	 * 주요활동 카드를 수정합니다.
	 *
	 * @param cardId 수정할 카드 ID
	 * @param request 수정 요청 DTO
	 * @return 수정된 카드 응답
	 */
	@Operation(summary = "주요활동 카드 수정", description = "지정한 주요활동 카드의 제목과 내용을 수정합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping("/activity/{cardId}")
	public ResponseEntity<ApiResponse<ActivityCardResponseDto>> updateActivityCard(
			@PathVariable Long cardId,
			@RequestBody @Valid ActivityCardUpdateRequestDto request) {
		return ResponseEntity.ok(ApiResponse.success(adminMainService.updateActivityCard(cardId, request)));
	}
}
