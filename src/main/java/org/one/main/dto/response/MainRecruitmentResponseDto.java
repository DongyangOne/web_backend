package org.one.main.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.recruitment.domain.Recruitment;

@Schema(description = "모집 공고 조회 응답")
@Getter
@Builder
@AllArgsConstructor
public class MainRecruitmentResponseDto {

    @Schema(description = "모집 대상", example = "웹응용소프트웨어공학과 1, 2학년")
    private String target;

    @Schema(description = "모집 분야", example = "Backend, Frontend, AI, Design")
    private String field;

    @Schema(description = "모집 시작일", example = "2026-04-01")
    private String recruitmentStart;

    @Schema(description = "모집 종료일", example = "2026-04-05")
    private String recruitmentEnd;

    @Schema(description = "면접 시작일", example = "2026-04-06")
    private String interviewStart;

    @Schema(description = "면접 종료일", example = "2026-04-07")
    private String interviewEnd;

    @Schema(description = "합격자 발표일", example = "2026-04-07")
    private String notificationDate;

    @Schema(description = "현재 모집 중 여부", example = "true")
    private Boolean recruiting;

    public static MainRecruitmentResponseDto of(Recruitment recruitment, boolean isRecruiting) {
        return MainRecruitmentResponseDto.builder()
                .target(recruitment.getTarget())
                .field(recruitment.getField())
                .recruitmentStart(recruitment.getRecruitmentStart() != null ? recruitment.getRecruitmentStart().toString() : null)
                .recruitmentEnd(recruitment.getRecruitmentEnd() != null ? recruitment.getRecruitmentEnd().toString() : null)
                .interviewStart(recruitment.getInterviewStart() != null ? recruitment.getInterviewStart().toString() : null)
                .interviewEnd(recruitment.getInterviewEnd() != null ? recruitment.getInterviewEnd().toString() : null)
                .notificationDate(recruitment.getNotificationDate() != null ? recruitment.getNotificationDate().toString() : null)
                .recruiting(isRecruiting)
                .build();
    }
}