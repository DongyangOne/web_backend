package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.one.domain.dto.request.MainLogoUpdateRequest;
import org.one.domain.dto.response.MainLogoResponse;
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
	 * @return 저장된 로고 URL
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
	public ResponseEntity<ApiResponse<MainLogoResponse>> updateLogo(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "수정할 로고 URL",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"logoUrl\":\"https://cdn.example.com/logo.png\"}")))
			@RequestBody @Valid MainLogoUpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success(adminMainService.updateLogo(request)));
	}
}
