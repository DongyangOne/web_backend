package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.response.PresignedUploadResponseDto;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.one.global.service.MinioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 파일 업로드용 Presigned URL 발급 API를 제공합니다.
 *
 * <p>클라이언트는 이 API로 Presigned PUT URL을 받아 MinIO에 직접 파일을 업로드합니다.
 * 업로드 완료 후 응답의 objectKey를 로고 수정, 프로젝트 생성 등 API에 전달합니다.
 */
@Tag(name = "File", description = "파일 업로드 Presigned URL 발급")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

	private final MinioService minioService;

	/**
	 * MinIO Presigned PUT URL을 발급합니다.
	 *
	 * <p>업로드 흐름:
	 * <ol>
	 *   <li>이 API 호출 → uploadUrl, objectKey 수령</li>
	 *   <li>uploadUrl로 HTTP PUT 요청하여 파일 직접 업로드</li>
	 *   <li>objectKey를 로고 수정 / 프로젝트 생성·수정 API에 전달</li>
	 * </ol>
	 *
	 * @param type 업로드 대상 유형 (logo | project)
	 * @return Presigned PUT URL 및 objectKey
	 */
	@Operation(
			summary = "Presigned 업로드 URL 발급",
			description = "클라이언트가 MinIO에 직접 파일을 업로드할 수 있는 Presigned PUT URL을 발급합니다. "
					+ "type=logo 이면 logo/ 경로, type=project 이면 projects/ 경로로 objectKey가 생성됩니다."
	)
	@ApiErrorExceptions({ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@GetMapping("/upload-url")
	public ResponseEntity<ApiResponse<PresignedUploadResponseDto>> getUploadUrl(
			@Parameter(description = "업로드 대상 유형 (logo | project)", example = "logo")
			@RequestParam String type) {
		String prefix = "project".equals(type) ? "projects/" : "logo/";
		String objectKey = prefix + UUID.randomUUID();
		String uploadUrl = minioService.generateUploadUrl(objectKey);
		return ResponseEntity.ok(ApiResponse.success(PresignedUploadResponseDto.of(uploadUrl, objectKey)));
	}
}
