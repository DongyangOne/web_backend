package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 캘린더 일정 생성 요청 DTO입니다.
 */
@Schema(description = "캘린더 일정 생성 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarSaveRequestDto {

	@Schema(description = "일정 제목", example = "MT")
	@NotBlank(message = "일정 제목을 입력해주세요.")
	@Size(max = 100, message = "일정 제목은 100자 이내로 입력해주세요.")
	private String title;

	@Schema(description = "일정 시작일", example = "2025-05-01")
	@NotNull(message = "시작일을 입력해주세요.")
	private LocalDate startDate;

	@Schema(description = "일정 종료일", example = "2025-05-03")
	@NotNull(message = "종료일을 입력해주세요.")
	private LocalDate endDate;
}
