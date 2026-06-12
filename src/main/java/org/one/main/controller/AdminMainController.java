package org.one.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.one.main.dto.request.ActivityCardUpdateRequestDto;
import org.one.main.dto.request.MainLogoUpdateRequestDto;
import org.one.main.dto.response.ActivityCardResponseDto;
import org.one.main.dto.response.MainLogoResponseDto;
import org.one.main.service.AdminMainService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 메인 페이지 API 요청을 받고 서비스 계층으로 위임합니다.
 */
@Tag(name = "Admin - Main", description = "관리자 메인 페이지")
@Validated
@RestController
@RequestMapping("/api/v1/admin/main")
@RequiredArgsConstructor
public class AdminMainController {

	private final AdminMainService adminMainService;

	/**
	 * 메인 페이지 로고 URL을 수정합니다.
	 * 클라이언트는 먼저 {@code GET /api/v1/files/upload-url?type=logo} 로 Presigned URL을 발급받아
	 * 파일을 MinIO에 직접 업로드한 뒤, 응답의 objectKey를 이 API에 전달합니다.
	 *
	 * @param request objectKey를 담은 요청 DTO
	 * @return 수정된 로고 응답
	 */
	@Operation(summary = "관리자 메인 로고 수정",
			description = "Presigned URL로 MinIO에 직접 업로드 완료 후, objectKey를 전달해 로고 URL을 저장합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping("/logo")
	public ResponseEntity<ApiResponse<MainLogoResponseDto>> update(
			@RequestBody @Valid MainLogoUpdateRequestDto request) {
		MainLogoResponseDto response = adminMainService.update(request.getObjectKey());
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	/**
	 * 주요활동 카드 내용을 수정합니다.
	 *
	 * @param cardId 수정할 카드 ID
	 * @param request 카드 수정 요청 DTO
	 * @return 수정된 카드 응답
	 */
	@Operation(summary = "주요활동 카드 수정", description = "주요활동 카드 제목과 내용을 수정합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping("/activity/{cardId}")
	public ResponseEntity<ApiResponse<ActivityCardResponseDto>> updateActivityCard(
			@PathVariable @Positive(message = "카드 ID는 양수여야 합니다.") Long cardId,
			@RequestBody @Valid ActivityCardUpdateRequestDto request) {
		ActivityCardResponseDto response = adminMainService.updateActivityCard(cardId, request);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	/**
	 * 주요활동 카드 내용을 초기화합니다.
	 *
	 * @param cardId 초기화할 카드 ID
	 * @return 초기화 완료 응답
	 */
	@Operation(summary = "주요활동 카드 초기화", description = "주요활동 카드 제목과 내용을 초기화합니다.")
	@ApiErrorExceptions({ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping("/activity/{cardId}/clear")
	public ResponseEntity<ApiResponse<Void>> clearActivityCard(
			@PathVariable @Positive(message = "카드 ID는 양수여야 합니다.") Long cardId) {
		adminMainService.clearActivityCard(cardId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
