package org.one.calendar.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.one.calendar.dto.request.CalendarSaveRequestDto;
import org.one.calendar.dto.request.CalendarUpdateRequestDto;
import org.one.calendar.dto.response.CalendarMonthlyResponseDto;
import org.one.calendar.dto.response.CalendarResponseDto;
import org.one.calendar.domain.CalendarSchedule;
import org.one.calendar.repository.CalendarScheduleRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 캘린더 일정 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminCalendarService {

	private static final DateTimeFormatter YEAR_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

	private final CalendarScheduleRepository calendarScheduleRepository;

	/**
	 * 캘린더 일정을 생성합니다.
	 *
	 * @param request 생성 요청 DTO
	 * @return 생성된 일정 응답 DTO
	 */
	public CalendarResponseDto save(CalendarSaveRequestDto request) {
		validateDateRange(request.getStartDate(), request.getEndDate());
		CalendarSchedule schedule = new CalendarSchedule(
				request.getTitle(),
				request.getStartDate(),
				request.getEndDate()
		);
		CalendarSchedule saved = calendarScheduleRepository.save(schedule);
		return CalendarResponseDto.from(saved);
	}

	/**
	 * 특정 연도의 캘린더 일정을 월별로 그룹핑하여 조회합니다.
	 *
	 * @param year 연도
	 * @return 월별 그룹핑된 일정 목록
	 */
	@Transactional(readOnly = true)
	public List<CalendarMonthlyResponseDto> findAllByYear(int year) {
		LocalDate start = LocalDate.of(year, 1, 1);
		LocalDate end = LocalDate.of(year, 12, 31);
		List<CalendarSchedule> schedules = calendarScheduleRepository.findByDateRange(start, end);

		Map<String, List<CalendarResponseDto>> grouped = schedules.stream()
				.collect(Collectors.groupingBy(
						s -> s.getStartDate().format(YEAR_MONTH_FORMAT),
						LinkedHashMap::new,
						Collectors.mapping(CalendarResponseDto::from, Collectors.toList())
				));

		return grouped.entrySet().stream()
				.map(entry -> CalendarMonthlyResponseDto.builder()
						.yearMonth(entry.getKey())
						.schedules(entry.getValue())
						.build())
				.toList();
	}

	/**
	 * 특정 연월의 캘린더 일정을 조회합니다.
	 *
	 * @param year 연도
	 * @param month 월
	 * @return 해당 월에 겹치는 일정 목록
	 */
	@Transactional(readOnly = true)
	public List<CalendarResponseDto> findAllByMonth(int year, int month) {
		YearMonth yearMonth = YearMonth.of(year, month);
		LocalDate start = yearMonth.atDay(1);
		LocalDate end = yearMonth.atEndOfMonth();
		return calendarScheduleRepository.findByDateRange(start, end).stream()
				.map(CalendarResponseDto::from)
				.toList();
	}

	/**
	 * 캘린더 일정을 수정합니다.
	 *
	 * @param calendarId 수정할 일정 ID
	 * @param request 수정 요청 DTO
	 * @return 수정된 일정 응답 DTO
	 */
	public CalendarResponseDto update(Long calendarId, CalendarUpdateRequestDto request) {
		validateDateRange(request.getStartDate(), request.getEndDate());
		CalendarSchedule schedule = calendarScheduleRepository.findById(calendarId)
				.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
		schedule.update(
				request.getTitle(),
				request.getStartDate(),
				request.getEndDate()
		);
		return CalendarResponseDto.from(schedule);
	}

	/**
	 * 선택한 캘린더 일정을 일괄 삭제합니다.
	 *
	 * @param calendarIds 삭제할 일정 ID 목록
	 */
	public void delete(List<Long> calendarIds) {
		long existCount = calendarScheduleRepository.countByCalendarIdIn(calendarIds);
		if (existCount != calendarIds.size()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		calendarScheduleRepository.deleteAllById(calendarIds);
	}

	/**
	 * 시작일이 종료일보다 늦지 않은지 검증합니다.
	 *
	 * @param start 시작일
	 * @param end 종료일
	 */
	private void validateDateRange(LocalDate start, LocalDate end) {
		if (start != null && end != null && start.isAfter(end)) {
			throw new BusinessException(ErrorCode.INVALID_DATE_RANGE);
		}
	}
}
