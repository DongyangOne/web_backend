package org.one.recruitment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 모집 공고 수정 요청 DTO입니다.
 */
@Schema(description = "모집 공고 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecruitmentUpdateRequestDto {

    @Schema(description = "모집 대상", example = "웹응용소프트웨어공학과 1~2학년")
    @Size(max = 255, message = "모집 대상은 255자 이내로 입력해주세요.")
    private String target;

    @Schema(description = "지원 분야", example = "프론트엔드, 백엔드")
    @Size(max = 1000, message = "지원 분야는 1000자 이내로 입력해주세요.")
    private String field;

    @Schema(description = "모집 시작일", example = "2026-05-10")
    private LocalDate recruitmentStart;

    @Schema(description = "모집 종료일", example = "2026-05-30")
    private LocalDate recruitmentEnd;

    @Schema(description = "면접 시작일", example = "2026-06-01")
    private LocalDate interviewStart;

    @Schema(description = "면접 종료일", example = "2026-06-10")
    private LocalDate interviewEnd;

    @Schema(description = "합격자 발표일", example = "2026-06-17")
    private LocalDate notificationDate;

    @Schema(description = "동아리 문의 번호", example = "010-1234-5678")
    @Size(max = 20, message = "문의 번호는 20자 이내로 입력해주세요.")
    @Pattern(regexp = "^[0-9-]+$", message = "전화번호는 숫자와 하이픈(-)만 입력 가능합니다.")
    private String contactNumber;

    @Schema(description = "동아리 방 위치", example = "3호관 5층")
    @Size(max = 100, message = "방 위치는 100자 이내로 입력해주세요.")
    private String roomLocation;

    @Schema(description = "회장 이름", example = "홍길동")
    @Size(max = 5, message = "회장 이름은 5자 이내로 입력해주세요.")
    private String bossName;
}