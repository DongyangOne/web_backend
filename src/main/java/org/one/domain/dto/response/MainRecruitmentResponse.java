package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

/**
 * 관리자 메인 모집기간 수정 응답 DTO입니다.
 *
 * @param isRecruiting    현재 모집 중 여부 (현재 날짜가 모집 기간 내이면 true)
 * @param recruitmentStart 모집 시작일
 * @param recruitmentEnd   모집 종료일
 */
@Schema(description = "모집기간 수정 응답")
public record MainRecruitmentResponse(
		@Schema(description = "현재 모집 중 여부", example = "true")
		boolean isRecruiting,

		@Schema(description = "모집 시작일", example = "2026-03-04")
		LocalDate recruitmentStart,

		@Schema(description = "모집 종료일", example = "2026-03-20")
		LocalDate recruitmentEnd
) {}
