package org.one.main.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.main.domain.MainPageConfig;
import org.one.project.dto.response.MainProjectCardResponseDto;

import java.util.List;

/**
 * 방문자 메인 페이지 dto
 */
@Schema(description = "메인페이지(방문자)")
@Getter
@Builder
@AllArgsConstructor
public class VisitorMainResponseDto {
    @Schema(description = "메인 로고 이미지 url", example = "https://minio-dongyangone.duckdns.org/one-files/logo/4132272b-25a1-4e74-b821-8be4547d33a3")
    private String logoUrl;
    @Schema(description = "동아리 설명", example = "동양미래대학교 IT 개발 동아리 ONE입니다. 함께 만들고 함께 성장합니다.")
    private String mainDescription;
    @Schema(description = "주요 활동 목록", example = "[{\"cardId\" : 5, \"title\" : \"팀 프로젝트\", \"content\" : \"매 학기 팀을 구성하여 실제 서비스 수준의 프로젝트를 기획·개발합니다. 기획부터 배포까지 전 과정을 경험할 수 있습니다.\", \"cardOrder\" : 1}]")
    private List<ActivityCardResponseDto> activityCards;
    @Schema(description = "프로젝트 카드 목록", example = "[{\"projectId\": 9, \"year\": \"2025\", \"projectName\": \"AI 학습 도우미 챗봇\", \"award\": \"한국정보기술학회 학술 발표\", \"activity\": \"AI/ML 스터디, 모델 개발\", \"startDate\": \"2025-09-01\", \"endDate\": \"2025-12-15\", \"participantCount\": 4, \"description\": \"LLM 기반 대학생 학습 지원 챗봇 서비스 개발 및 학술 발표\", \"techStacks\": [\"Python\", \"FastAPI\", \"OpenAI\"], \"photos\": [{\"id\": 3, \"url\": \"https://cdn.example.com/projects/project9_photo1.jpg\"}]}]")
    private List<MainProjectCardResponseDto> projectDetails;

    public static VisitorMainResponseDto of(
            MainPageConfig mainPageConfig,
            List<ActivityCardResponseDto> activityCards,
            List<MainProjectCardResponseDto> projectDetails) {

        return VisitorMainResponseDto.builder()
                .logoUrl(mainPageConfig.getLogoUrl())
                .mainDescription(mainPageConfig.getDescription())
                .activityCards(activityCards)
                .projectDetails(projectDetails)
                .build();
    }
}
