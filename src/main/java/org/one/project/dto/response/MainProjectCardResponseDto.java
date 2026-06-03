package org.one.project.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.project.domain.ProjectEvent;
import org.one.project.domain.ProjectTechStack;

import java.util.Collections;
import java.util.List;

@Schema(description = "메인 페이지 프로젝트 카드 응답")
@Getter
@Builder
@AllArgsConstructor
public class MainProjectCardResponseDto {

    @Schema(description = "프로젝트 ID", example = "1")
    private Long projectId;

    @Schema(description = "년도", example = "2023")
    private String year;

    @Schema(description = "프로젝트명", example = "Around Music")
    private String projectName;

    @Schema(description = "수상 내역", example = "동양미래 EXPO 장려상")
    private String award;

    @Schema(description = "활동 내역", example = "2023 하계 MT · 정기 세미나 및 튜터링 운영")
    private String activity;

    @Schema(description = "시작일", example = "2023-03-01")
    private String startDate;

    @Schema(description = "종료일", example = "2023-10-31")
    private String endDate;

    @Schema(description = "참여 인원", example = "8")
    private Integer participantCount;

    @Schema(description = "프로젝트 설명", example = "Around Music은 음악과 AR을 통해 사용자의 순간과 감정을 공유하는 서비스이다.")
    private String description;

    @Schema(description = "기술 스택 목록", example = "[\"Android Studio\", \"React Native\", \"Spring Boot\"]")
    private List<String> techStacks;

    @Schema(description = "프로젝트 사진 목록", example = "[{\"id\": 1, \"url\": \"https://cdn.example.com/projects/photo.jpg\"}]")
    private List<ProjectPhotoResponseDto> photos;

    /**
     * ProjectEvent 엔티티에서 메인 페이지용 풀옵션 DTO를 생성합니다.
     */
    public static MainProjectCardResponseDto from(ProjectEvent project) {
        return MainProjectCardResponseDto.builder()
                .projectId(project.getProjectId())
                .year(project.getYear())
                .projectName(project.getProjectName())
                .award(project.getAward())
                .activity(project.getActivity())
                .startDate(project.getStartDate() != null ? project.getStartDate().toString() : null)
                .endDate(project.getEndDate() != null ? project.getEndDate().toString() : null)
                .participantCount(project.getParticipantCount())
                .description(project.getDescription())

                .techStacks(project.getTechStacks() == null ? Collections.emptyList() :
                        project.getTechStacks().stream()
                                .map(ProjectTechStack::getName)
                                .toList())
                .photos(project.getPhotos() == null ? Collections.emptyList() :
                        project.getPhotos().stream()
                                .map(ProjectPhotoResponseDto::from)
                                .toList())
                .build();
    }
}