package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.one.domain.entity.ProjectEvent;

/**
 * 프로젝트 응답 DTO입니다.
 *
 * @param projectId 프로젝트 ID
 * @param projectName 프로젝트명
 * @param participantCount 참여 인원
 * @param description 프로젝트 설명
 * @param priority 노출 순서
 * @param photos 사진 목록 (노출 순서 오름차순)
 */
@Schema(description = "프로젝트 응답")
public record ProjectResponse(
		@Schema(description = "프로젝트 ID", example = "1")
		Long projectId,

		@Schema(description = "프로젝트명", example = "ONE 웹사이트")
		String projectName,

		@Schema(description = "참여 인원", example = "6")
		Integer participantCount,

		@Schema(description = "프로젝트 설명", example = "동아리 공식 웹사이트 개발 프로젝트입니다.")
		String description,

		@Schema(description = "노출 순서", example = "0")
		Integer priority,

		@Schema(description = "사진 목록")
		List<PhotoResponse> photos
) {
	/**
	 * ProjectEvent 엔티티로부터 응답 DTO를 생성합니다.
	 *
	 * @param event 프로젝트 행사 엔티티
	 * @return ProjectResponse
	 */
	public static ProjectResponse from(ProjectEvent event) {
		List<PhotoResponse> photoResponses = event.getPhotos().stream()
				.map(PhotoResponse::from)
				.toList();
		return new ProjectResponse(
				event.getProjectId(),
				event.getProjectName(),
				event.getParticipantCount(),
				event.getDescription(),
				event.getPriority(),
				photoResponses
		);
	}
}
