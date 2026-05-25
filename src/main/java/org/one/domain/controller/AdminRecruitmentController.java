package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.RecruitmentUpdateRequestDto;
import org.one.domain.dto.response.RecruitmentResponseDto;
import org.one.domain.service.AdminRecruitmentService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 모집 공고 API 요청을 받고 서비스 계층으로 위임합니다.
 */
@Tag(name = "Admin - Recruitment", description = "관리자 모집 공고 관리")
@RestController
@RequestMapping("/api/v1/admin/recruitment")
@RequiredArgsConstructor
public class AdminRecruitmentController {

	private final AdminRecruitmentService adminRecruitmentService;

	/**
	 * 현재 모집 공고 정보를 조회합니다.
	 *
	 * @return 모집 공고 응답
	 */
	@Operation(summary = "모집 공고 조회", description = "현재 설정된 모집 공고 정보와 모집 중 여부를 조회합니다.")
	@ApiErrorExceptions({ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@GetMapping
	public ResponseEntity<ApiResponse<RecruitmentResponseDto>> findOne() {
		RecruitmentResponseDto response = adminRecruitmentService.findOne();
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	/**
	 * 모집 공고 정보를 수정합니다.
	 *
	 * @param request 수정 요청 DTO
	 * @return 수정된 모집 공고 응답
	 */
	@Operation(summary = "모집 공고 수정", description = "모집 공고 제목, 대상, 모집/면접 기간, 발표일을 수정합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping
	public ResponseEntity<ApiResponse<RecruitmentResponseDto>> update(
			@Valid @RequestBody RecruitmentUpdateRequestDto request) {
		RecruitmentResponseDto response = adminRecruitmentService.update(request);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
