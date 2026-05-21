package org.one.domain.service;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.CalendarSaveRequestDto;
import org.one.domain.dto.response.CalendarResponseDto;
import org.one.domain.entity.CalendarSchedule;
import org.one.domain.repository.CalendarScheduleRepository;
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
	 * 시작일이 종료일보다 늦지 않은지 검증합니다.
	 *
	 * @param start 시작일
	 * @param end 종료일
	 */
	private void validateDateRange(LocalDate start, LocalDate end) {
		if (start != null && end != null && start.isAfter(end)) {
			throw new BusinessException(ErrorCode.INVALID_INPUT);
		}
	}
}
