package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * 관리자 메인 모집 기간 수정 요청 DTO입니다.
 *
 * @param recruitmentStart 모집 시작일
 * @param recruitmentEnd 모집 종료일
 */
@Schema(description = "모집 기간 수정 요청")
public record MainRecruitmentUpdateRequest(
		@Schema(description = "모집 시작일", example = "2026-03-04")
		@NotNull(message = "모집 시작일을 입력해주세요.")
		LocalDate recruitmentStart,

		@Schema(description = "모집 종료일", example = "2026-03-20")
		@NotNull(message = "모집 종료일을 입력해주세요.")
		LocalDate recruitmentEnd
) {}
