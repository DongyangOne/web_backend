package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 관리자 메인 모집기간 수정 응답 DTO입니다.
 */
@Schema(description = "모집기간 수정 응답")
@Getter
@Builder
@AllArgsConstructor
public class MainRecruitmentResponseDto {

	@Schema(description = "현재 모집 중 여부", example = "true")
	private Boolean isRecruiting;

	@Schema(description = "모집 시작일", example = "2026-03-04")
	private LocalDate recruitmentStart;

	@Schema(description = "모집 종료일", example = "2026-03-20")
	private LocalDate recruitmentEnd;

	public static MainRecruitmentResponseDto from(Boolean isRecruiting, LocalDate recruitmentStart, LocalDate recruitmentEnd) {
		return MainRecruitmentResponseDto.builder()
				.isRecruiting(isRecruiting)
				.recruitmentStart(recruitmentStart)
				.recruitmentEnd(recruitmentEnd)
				.build();
	}
}
