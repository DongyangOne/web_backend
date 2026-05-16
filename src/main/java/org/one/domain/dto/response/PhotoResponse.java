package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.one.domain.entity.ProjectPhoto;

/**
 * 프로젝트 사진 응답 DTO입니다.
 *
 * @param photoId 사진 ID
 * @param photoUrl 사진 URL
 * @param priority 노출 순서
 */
@Schema(description = "프로젝트 사진 응답")
public record PhotoResponse(
		@Schema(description = "사진 ID", example = "1")
		Long photoId,

		@Schema(description = "사진 URL", example = "https://cdn.example.com/photo1.jpg")
		String photoUrl,

		@Schema(description = "노출 순서", example = "0")
		Integer priority
) {
	/**
	 * ProjectPhoto 엔티티로부터 응답 DTO를 생성합니다.
	 *
	 * @param photo 프로젝트 사진 엔티티
	 * @return PhotoResponse
	 */
	public static PhotoResponse from(ProjectPhoto photo) {
		return new PhotoResponse(photo.getPhotoId(), photo.getPhotoUrl(), photo.getPriority());
	}
}
