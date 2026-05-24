package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관리자 메인 로고 수정 요청 DTO입니다.
 *
 * <p>클라이언트는 먼저 {@code GET /api/v1/files/upload-url?type=logo} 로 Presigned URL을 발급받아
 * 파일을 MinIO에 직접 업로드한 뒤, 응답의 objectKey를 이 DTO에 담아 전달합니다.
 */
@Schema(description = "로고 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainLogoUpdateRequestDto {

	@Schema(description = "Presigned URL 발급 시 받은 objectKey", example = "logo/550e8400-e29b-41d4-a716-446655440000")
	@NotBlank(message = "objectKey는 필수입니다.")
	@Size(max = 500, message = "objectKey는 500자 이하여야 합니다.")
	private String objectKey;
}
