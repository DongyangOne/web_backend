package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.one.domain.dto.request.PhotoReorderRequest;
import org.one.domain.dto.request.ProjectUpdateRequest;
import org.one.domain.dto.response.ProjectResponse;
import org.one.domain.service.AdminProjectService;
import org.one.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 프로젝트 관리 API를 제공합니다.
 */
@Tag(name = "Admin Project", description = "관리자 프로젝트 관리")
@RestController
@RequestMapping("/api/admin/projects")
public class AdminProjectController {

	private final AdminProjectService adminProjectService;

	/**
	 * 관리자 프로젝트 서비스를 주입받습니다.
	 *
	 * @param adminProjectService 관리자 프로젝트 서비스
	 */
	public AdminProjectController(AdminProjectService adminProjectService) {
		this.adminProjectService = adminProjectService;
	}

	/**
	 * 프로젝트 기본 정보를 수정합니다. 사진은 별도 엔드포인트로 관리합니다.
	 *
	 * @param projectId 수정할 프로젝트 ID
	 * @param request 프로젝트 수정 요청
	 * @return 수정된 프로젝트 정보
	 */
	@Operation(summary = "프로젝트 수정", description = "프로젝트 기본 정보(이름/인원/설명/순서)를 수정합니다. 사진은 /photos 엔드포인트로 별도 관리합니다.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "수정 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "프로젝트를 찾을 수 없음"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PatchMapping("/{projectId}")
	public ResponseEntity<ApiResponse<ProjectResponse>> update(
			@Parameter(description = "프로젝트 ID", example = "1") @PathVariable Long projectId,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "프로젝트 수정 정보",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"projectName\":\"ONE 웹사이트 v2\",\"participantCount\":8,\"description\":\"리뉴얼 개발\",\"priority\":0}")))
			@RequestBody @Valid ProjectUpdateRequest request) {
		return ResponseEntity.ok(ApiResponse.success(adminProjectService.update(projectId, request)));
	}

	// ── 사진 관리 ──────────────────────────────────────────────────────────────

	/**
	 * 사진을 삭제합니다.
	 *
	 * @param projectId 프로젝트 ID
	 * @param photoId 사진 ID
	 * @return 빈 응답
	 */
	@Operation(summary = "사진 삭제", description = "프로젝트에서 사진을 삭제합니다.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "삭제 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사진을 찾을 수 없음"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@DeleteMapping("/{projectId}/photos/{photoId}")
	public ResponseEntity<ApiResponse<Void>> deletePhoto(
			@Parameter(description = "프로젝트 ID", example = "1") @PathVariable Long projectId,
			@Parameter(description = "사진 ID", example = "1") @PathVariable Long photoId) {
		adminProjectService.deletePhoto(projectId, photoId);
		return ResponseEntity.ok(ApiResponse.success(null));
	}

	/**
	 * 프로젝트 사진 순서를 일괄 변경합니다.
	 *
	 * @param projectId 프로젝트 ID
	 * @param request 순서 변경 요청
	 * @return 변경 후 프로젝트 응답
	 */
	@Operation(summary = "사진 순서 변경", description = "사진 ID와 새 순서를 전달해 일괄 변경합니다. 요청에 없는 사진은 그대로 유지됩니다.")
	@ApiResponses({
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "변경 성공"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", ref = "#/components/responses/BadRequest"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", ref = "#/components/responses/Unauthorized"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", ref = "#/components/responses/Forbidden"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사진을 찾을 수 없음"),
			@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", ref = "#/components/responses/InternalServerError")
	})
	@PatchMapping("/{projectId}/photos/order")
	public ResponseEntity<ApiResponse<ProjectResponse>> reorderPhotos(
			@Parameter(description = "프로젝트 ID", example = "1") @PathVariable Long projectId,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "변경할 사진 순서 목록",
					content = @Content(mediaType = "application/json", examples = @ExampleObject(
							value = "{\"orders\":[{\"photoId\":1,\"priority\":0},{\"photoId\":2,\"priority\":1}]}")))
			@RequestBody @Valid PhotoReorderRequest request) {
		return ResponseEntity.ok(ApiResponse.success(adminProjectService.reorderPhotos(projectId, request)));
	}
}
