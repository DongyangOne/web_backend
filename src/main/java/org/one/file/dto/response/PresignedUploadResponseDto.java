package org.one.file.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Presigned 업로드 URL 발급 응답 DTO입니다.
 *
 * <p>클라이언트는 uploadUrl로 HTTP PUT 요청을 보내 파일을 MinIO에 직접 업로드합니다.
 * 업로드 완료 후 objectKey를 이후 API(로고 수정, 프로젝트 생성 등)에 전달합니다.
 */
@Schema(description = "Presigned 업로드 URL 응답")
@Getter
@Builder
@AllArgsConstructor
public class PresignedUploadResponseDto {

	@Schema(description = "MinIO Presigned PUT URL — 이 URL로 HTTP PUT 요청을 보내 파일을 업로드합니다.",
			example = "http://minio:9000/one-bucket/logo/uuid?X-Amz-Signature=...")
	private String uploadUrl;

	@Schema(description = "업로드 후 저장할 객체 키 — 로고·프로젝트 등록 API에 이 값을 전달합니다.",
			example = "logo/550e8400-e29b-41d4-a716-446655440000")
	private String objectKey;

	/**
	 * Presigned 업로드 URL 응답 DTO를 생성합니다.
	 *
	 * @param uploadUrl Presigned PUT URL
	 * @param objectKey MinIO 객체 키
	 * @return 응답 DTO
	 */
	public static PresignedUploadResponseDto of(String uploadUrl, String objectKey) {
		return PresignedUploadResponseDto.builder()
				.uploadUrl(uploadUrl)
				.objectKey(objectKey)
				.build();
	}
}
