package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
}
