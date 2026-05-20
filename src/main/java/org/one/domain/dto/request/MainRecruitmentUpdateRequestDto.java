package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * 관리자 메인 모집기간 수정 요청 DTO입니다.
 */
@Schema(description = "모집기간 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainRecruitmentUpdateRequestDto {

	@Schema(description = "모집 시작일", example = "2026-03-04")
	@NotNull(message = "모집 시작일은 필수입니다.")
	private LocalDate recruitmentStart;

	@Schema(description = "모집 종료일", example = "2026-03-20")
	@NotNull(message = "모집 종료일은 필수입니다.")
	private LocalDate recruitmentEnd;
}
