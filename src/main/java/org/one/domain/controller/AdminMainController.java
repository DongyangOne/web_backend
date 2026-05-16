package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.one.domain.dto.request.MainIntroUpdateRequest;
import org.one.domain.dto.request.MainLogoUpdateRequest;
import org.one.domain.dto.request.MainRecruitmentUpdateRequest;
import org.one.domain.dto.response.AdminMainSummaryResponse;
import org.one.domain.service.AdminMainService;
import org.one.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 메인 페이지(캘린더/프로젝트 제외) API를 제공합니다.
 */
@Tag(name = "Admin Main", description = "관리자 메인 페이지")
@RestController
@RequestMapping("/api/admin/main")
public class AdminMainController {

	private final AdminMainService adminMainService;

	/**
	 * 관리자 메인 서비스를 주입받습니다.
	 *
	 * @param adminMainService 관리자 메인 서비스
	 */
	public AdminMainController(AdminMainService adminMainService) {
		this.adminMainService = adminMainService;
	}

	/**
	 * 관리자 메인 로고를 수정합니다.
	 *
	 * @param request 로고 수정 요청
	 * @return 수정 후 메인 요약 정보
	 */
	@Operation(summary = "관리자 메인 로고 수정", description = "로고 URL을 즉시 저장합니다.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PatchMapping("/logo")
	public ResponseEntity<ApiResponse<AdminMainSummaryResponse>> updateLogo(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "수정할 로고 URL",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"logoUrl\":\"https://cdn.example.com/logo.png\"}")))
			@RequestBody @Valid MainLogoUpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success(adminMainService.updateLogo(request)));
	}

	/**
	 * 관리자 메인 소개 문구를 수정합니다.
	 *
	 * @param request 소개 수정 요청
	 * @return 수정 후 메인 요약 정보
	 */
	@Operation(summary = "관리자 메인 소개 수정", description = "소개 문구를 즉시 저장합니다.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PatchMapping("/intro")
	public ResponseEntity<ApiResponse<AdminMainSummaryResponse>> updateIntro(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "수정할 소개 문구",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"description\":\"ONE 동아리 소개 수정\"}")))
			@RequestBody @Valid MainIntroUpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success(adminMainService.updateIntro(request)));
	}

	/**
	 * 관리자 메인 모집 기간을 수정합니다.
	 *
	 * @param request 모집기간 수정 요청
	 * @return 수정 후 메인 요약 정보
	 */
	@Operation(summary = "관리자 메인 모집기간 수정", description = "모집 시작일과 종료일을 즉시 저장합니다. isRecruiting은 현재 날짜가 범위 내에 있으면 자동으로 true가 됩니다.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PatchMapping("/recruitment")
	public ResponseEntity<ApiResponse<AdminMainSummaryResponse>> updateRecruitment(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "모집 시작/종료일",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"recruitmentStart\":\"2026-03-04\",\"recruitmentEnd\":\"2026-03-20\"}")))
			@RequestBody @Valid MainRecruitmentUpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success(adminMainService.updateRecruitment(request)));
	}
}
