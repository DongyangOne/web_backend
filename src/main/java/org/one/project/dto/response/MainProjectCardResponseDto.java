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

    @Schema(description = "프로젝트 ID", example = "9")
    private Long projectId;

    @Schema(description = "년도", example = "2025")
    private String year;

    @Schema(description = "프로젝트명", example = "AI 학습 도우미 챗봇")
    private String projectName;

    @Schema(description = "수상 내역", example = "한국정보기술학회 학술 발표")
    private String award;

    @Schema(description = "활동 내역", example = "AI/ML 스터디, 모델 개발")
    private String activity;

    @Schema(description = "시작일", example = "2025-09-01")
    private String startDate;

    @Schema(description = "종료일", example = "2025-12-15")
    private String endDate;

    @Schema(description = "참여 인원", example = "4")
    private Integer participantCount;

    @Schema(description = "프로젝트 설명", example = "LLM 기반 대학생 학습 지원 챗봇 서비스 개발 및 학술 발표")
    private String description;

    @Schema(description = "기술 스택 목록", example = "[\"Python\", \"FastAPI\", \"OpenAI\"]")
    private List<String> techStacks;

    @Schema(description = "프로젝트 사진 목록", example = "[{\"id\": 3, \"url\": \"https://cdn.example.com/projects/project9_photo1.jpg\"}]")
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