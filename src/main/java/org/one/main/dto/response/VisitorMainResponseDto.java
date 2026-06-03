package org.one.main.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.main.domain.MainPageConfig;
import org.one.project.dto.response.MainProjectCardResponseDto;
import org.one.project.dto.response.ProjectDetailResponseDto;
import org.one.recruitment.dto.response.MainRecruitmentResponseDto;
import org.one.recruitment.dto.response.RecruitmentResponseDto;

import java.util.List;

/**
 *  방문자 메인 페이지 dto
 */
@Schema(description = "메인페이지(방문자)")
@Getter
@Builder
@AllArgsConstructor
public class VisitorMainResponseDto {
    @Schema(description = "메인 로고 이미지 url", example = "https://cdn.example.com/logo.png")
    private String logoUrl;
    @Schema(description = "동아리 설명", example = "아이디어를 현실로 구현하는 공간 one")
    private String mainDescription;
    @Schema(description = "주요 활동 목록", example = "[{\"cardId\" : 3, \"title\" : \"스터디\", \"content\" : \"전공 지식과 최신 기술을 함께 공부하며 꾸준한 성장을 목표로 합니다.\", \"cardOrder\" : 3}]")
    private List<ActivityCardResponseDto> activityCards;
    @Schema(description = "프로젝트 카드 목록", example = "[{\"projectId\": 1, \"year\": \"2023\", \"projectName\": \"Around Music\", \"award\": \"동양미래 EXPO 장려상\", \"activity\": \"2023 하계 MT · 정기 세미나 및 튜터링 운영\", \"startDate\": \"2023-03-01\", \"endDate\": \"2023-10-31\", \"participantCount\": 8, \"description\": \"Around Music은 음악과 AR을 통해 사용자의 순간과 감정을 공유하는 서비스이다.\", \"techStacks\": [\"Android Studio\", \"React Native\", \"Spring Boot\"], \"photos\": [{\"id\": 1, \"url\": \"https://cdn.example.com/projects/photo.jpg\"}]}]")
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
