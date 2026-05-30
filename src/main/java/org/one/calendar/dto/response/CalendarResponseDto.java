package org.one.calendar.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.calendar.domain.CalendarSchedule;

/**
 * 캘린더 일정 응답 DTO입니다.
 */
@Schema(description = "캘린더 일정 응답")
@Getter
@Builder
@AllArgsConstructor
public class CalendarResponseDto {

	@Schema(description = "일정 ID", example = "1")
	private Long calendarId;

	@Schema(description = "일정 제목", example = "MT")
	private String title;

	@Schema(description = "일정 시작일", example = "2025-05-01")
	private LocalDate startDate;

	@Schema(description = "일정 종료일", example = "2025-05-03")
	private LocalDate endDate;

	/**
	 * CalendarSchedule 엔티티에서 응답 DTO를 생성합니다.
	 *
	 * @param schedule 캘린더 일정 엔티티
	 * @return 캘린더 일정 응답 DTO
	 */
	public static CalendarResponseDto from(CalendarSchedule schedule) {
		return CalendarResponseDto.builder()
				.calendarId(schedule.getCalendarId())
				.title(schedule.getTitle())
				.startDate(schedule.getStartDate())
				.endDate(schedule.getEndDate())
				.build();
	}
}
