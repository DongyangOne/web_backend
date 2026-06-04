package org.one.calendar.service;

import lombok.RequiredArgsConstructor;
import org.one.calendar.dto.response.CalendarResponseDto;
import org.one.calendar.repository.CalendarScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorCalendarService {
    private final CalendarScheduleRepository calendarScheduleRepository;

    /**
     * 특정 연월의 캘린더 일정을 조회합니다.
     *
     * @param year  연도
     * @param month 월
     * @return 해당 월에 겹치는 일정 목록
     */
    public List<CalendarResponseDto> getMonthScheduleList(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();
        return calendarScheduleRepository.findByDateRange(start, end).stream()
                .map(CalendarResponseDto::from)
                .toList();
    }
}
