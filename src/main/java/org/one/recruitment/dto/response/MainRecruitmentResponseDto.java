package org.one.recruitment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.one.recruitment.domain.Recruitment;

@Schema(description = "메인 페이지용 모집 공고 응답")
@Getter
@Builder
public class MainRecruitmentResponseDto {

    @Schema(description = "모집 대상", example = "웹응용소프트웨어공학과 1, 2학년")
    private String target;

    @Schema(description = "모집 분야", example = "Backend, Frontend, AI, Design")
    private String field;

    @Schema(description = "모집 시작일", example = "2026-05-10")
    private String recruitmentStart;

    @Schema(description = "모집 종료일", example = "2026-05-30")
    private String recruitmentEnd;

    @Schema(description = "현재 모집 중 여부", example = "true")
    @JsonProperty("isRecruiting")
    private boolean isRecruiting;

    // 엔티티와 모집 여부를 받아서 내 DTO로 변환
    public static MainRecruitmentResponseDto from(Recruitment recruitment, boolean isRecruiting) {
        return MainRecruitmentResponseDto.builder()
                .target(recruitment.getTarget())
                .field(recruitment.getField())
                .recruitmentStart(recruitment.getRecruitmentStart().toString())
                .recruitmentEnd(recruitment.getRecruitmentEnd().toString())
                .isRecruiting(isRecruiting)
                .build();
    }
}