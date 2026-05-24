package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ProjectSaveRequestDto;
import org.one.domain.dto.request.ProjectUpdateRequestDto;
import org.one.domain.dto.response.ProjectDetailResponseDto;
import org.one.domain.service.AdminProjectService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 프로젝트 API 요청을 받고 서비스 계층으로 위임합니다.
 */
@Tag(name = "Admin - Project", description = "관리자 프로젝트 관리")
@RestController
@RequestMapping("/api/v1/admin/project")
@RequiredArgsConstructor
public class AdminProjectController {

	private final AdminProjectService adminProjectService;

	/**
	 * 프로젝트를 생성합니다.
	 * 사진 첨부 시 먼저 {@code GET /api/v1/files/upload-url?type=project} 로 Presigned URL을 발급받아
	 * MinIO에 직접 업로드 후, objectKey 목록을 request의 photoKeys에 담아 전달합니다.
	 *
	 * @param request 생성 요청 DTO (photoKeys: 업로드 완료한 objectKey 목록)
	 * @return 생성된 프로젝트 상세 정보
	 */
	@Operation(summary = "프로젝트 생성",
			description = "새로운 프로젝트를 등록합니다. 사진은 Presigned URL로 MinIO에 직접 업로드 후 objectKey를 전달합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@PostMapping
	public ResponseEntity<ApiResponse<ProjectDetailResponseDto>> save(
			@RequestBody @Valid ProjectSaveRequestDto request) {
		ProjectDetailResponseDto response = adminProjectService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}

	/**
	 * 프로젝트 정보를 수정합니다.
	 * keepPhotoIds로 유지할 기존 사진을 지정하고, 새 사진은 Presigned URL로 MinIO에 직접 업로드 후
	 * objectKey 목록을 request의 newPhotoKeys에 담아 전달합니다.
	 *
	 * @param projectId 수정할 프로젝트 ID
	 * @param request 수정 요청 DTO (keepPhotoIds + newPhotoKeys)
	 * @return 수정된 프로젝트 상세 정보
	 */
	@Operation(summary = "프로젝트 수정",
			description = "프로젝트 정보를 수정합니다. keepPhotoIds로 유지할 사진을 지정하고, 새 사진은 Presigned URL 업로드 후 objectKey를 전달합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping("/{projectId}")
	public ResponseEntity<ApiResponse<ProjectDetailResponseDto>> update(
			@PathVariable Long projectId,
			@RequestBody @Valid ProjectUpdateRequestDto request) {
		ProjectDetailResponseDto response = adminProjectService.update(projectId, request);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
