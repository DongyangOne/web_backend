package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ProjectUpdateRequestDto;
import org.one.domain.dto.response.ProjectDetailResponseDto;
import org.one.domain.service.AdminProjectService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	 * 프로젝트 정보를 수정합니다.
	 * keepPhotoIds로 유지할 기존 사진을 지정하고, newPhotos로 새 사진을 추가합니다.
	 * 최종 사진 수는 1~3개여야 합니다.
	 *
	 * @param projectId 수정할 프로젝트 ID
	 * @param request 수정 요청 DTO (keepPhotoIds 포함)
	 * @param newPhotos 새로 추가할 사진 파일 목록 (선택, 이미지만 허용)
	 * @return 수정된 프로젝트 상세 정보
	 */
	@Operation(summary = "프로젝트 수정", description = "프로젝트 정보를 수정합니다. keepPhotoIds로 유지할 사진을 지정하고, newPhotos로 사진을 추가합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping(value = "/{projectId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<ProjectDetailResponseDto>> update(
			@PathVariable Long projectId,
			@RequestPart @Valid ProjectUpdateRequestDto request,
			@RequestPart(required = false) List<MultipartFile> newPhotos) {
		ProjectDetailResponseDto response = adminProjectService.update(projectId, request, newPhotos);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
}
