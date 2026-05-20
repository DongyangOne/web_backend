package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.MainLogoUpdateRequestDto;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.service.AdminMainService;
import org.one.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin Main", description = "관리자 메인 페이지")
@RestController
@RequestMapping("/api/admin/main")
@RequiredArgsConstructor
public class AdminMainController {

	private final AdminMainService adminMainService;

	@Operation(summary = "관리자 메인 로고 수정", description = "로고 URL을 즉시 저장합니다.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PatchMapping("/logo")
	public ResponseEntity<ApiResponse<MainLogoResponseDto>> updateLogo(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "수정할 로고 URL",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"logoUrl\":\"https://cdn.example.com/logo.png\"}")))
			@RequestBody @Valid MainLogoUpdateRequestDto request) {
		return ResponseEntity.ok(ApiResponse.success(adminMainService.updateLogo(request)));
	}
}
