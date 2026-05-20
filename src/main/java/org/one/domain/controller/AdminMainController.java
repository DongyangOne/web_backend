package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.response.MainLogoResponseDto;
import org.one.domain.service.AdminMainService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	 * 메인 페이지 로고 이미지를 수정합니다. MinIO에 업로드 후 URL을 저장합니다.
	 *
	 * @param logo 로고 이미지 파일 (이미지만 허용)
	 * @return 수정된 로고 응답
	 */
	@Operation(summary = "관리자 메인 로고 수정", description = "로고 이미지를 업로드합니다. 이미지 파일만 허용됩니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping(value = "/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<MainLogoResponseDto>> update(
			@RequestPart MultipartFile logo) {
		MainLogoResponseDto response = adminMainService.update(logo);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
