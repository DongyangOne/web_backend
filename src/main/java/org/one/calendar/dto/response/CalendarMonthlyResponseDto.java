package org.one.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 월별로 그룹핑된 캘린더 일정 응답 DTO입니다.
 */
@Schema(description = "월별 캘린더 일정 응답")
@Getter
@Builder
@AllArgsConstructor
public class CalendarMonthlyResponseDto {

	@Schema(description = "연월", example = "2026-05")
	private String yearMonth;

	@Schema(description = "해당 월의 일정 목록")
	private List<CalendarResponseDto> schedules;
}
