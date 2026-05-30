package org.one.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.project.domain.ProjectPhoto;

/**
 * 프로젝트 사진 응답 DTO입니다.
 */
@Schema(description = "프로젝트 사진")
@Getter
@Builder
@AllArgsConstructor
public class ProjectPhotoResponseDto {

	@Schema(description = "사진 ID", example = "1")
	private Long id;

	@Schema(description = "사진 URL", example = "https://cdn.example.com/projects/photo.jpg")
	private String url;

	/**
	 * ProjectPhoto 엔티티에서 응답 DTO를 생성합니다.
	 *
	 * @param photo 프로젝트 사진 엔티티
	 * @return 프로젝트 사진 응답 DTO
	 */
	public static ProjectPhotoResponseDto from(ProjectPhoto photo) {
		return ProjectPhotoResponseDto.builder()
				.id(photo.getPhotoId())
				.url(photo.getPhotoUrl())
				.build();
	}
}
