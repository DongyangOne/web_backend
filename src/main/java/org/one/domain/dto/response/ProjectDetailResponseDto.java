package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.domain.entity.ProjectEvent;
import org.one.domain.entity.ProjectTechStack;

/**
 * 프로젝트 상세 응답 DTO입니다.
 */
@Schema(description = "프로젝트 상세 응답")
@Getter
@Builder
@AllArgsConstructor
public class ProjectDetailResponseDto {

	@Schema(description = "프로젝트 ID", example = "1")
	private Long projectId;

	@Schema(description = "년도", example = "2026")
	private String year;

	@Schema(description = "프로젝트명", example = "ONE 웹 서비스")
	private String projectName;

	@Schema(description = "수상 내역", example = "최우수상")
	private String award;

	@Schema(description = "해당 연도 활동 내역", example = "2023 하계 MT · 정기 세미나 및 튜터링 운영")
	private String activity;

	@Schema(description = "프로젝트 시작일", example = "2026-03-01")
	private LocalDate startDate;

	@Schema(description = "프로젝트 종료일", example = "2026-06-30")
	private LocalDate endDate;

	@Schema(description = "팀원 수", example = "6")
	private Integer participantCount;

	@Schema(description = "기술 스택 목록", example = "[\"React\", \"Spring Boot\", \"MySQL\"]")
	private List<String> techStacks;

	@Schema(description = "프로젝트 소개")
	private String description;

	@Schema(description = "사진 목록 (ID 포함)")
	private List<ProjectPhotoResponseDto> photos;

	/**
	 * ProjectEvent 엔티티에서 상세 응답 DTO를 생성합니다.
	 *
	 * @param project 프로젝트 행사 엔티티
	 * @return 프로젝트 상세 응답 DTO
	 */
	public static ProjectDetailResponseDto from(ProjectEvent project) {
		return ProjectDetailResponseDto.builder()
				.projectId(project.getProjectId())
				.year(project.getYear())
				.projectName(project.getProjectName())
				.award(project.getAward())
				.activity(project.getActivity())
				.startDate(project.getStartDate())
				.endDate(project.getEndDate())
				.participantCount(project.getParticipantCount())
				.techStacks(project.getTechStacks().stream()
						.map(ProjectTechStack::getName)
						.toList())
				.description(project.getDescription())
				.photos(project.getPhotos().stream()
						.map(ProjectPhotoResponseDto::from)
						.toList())
				.build();
	}
}
