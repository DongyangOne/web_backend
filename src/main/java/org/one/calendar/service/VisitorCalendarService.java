package org.one.calendar.service;

import lombok.RequiredArgsConstructor;
import org.one.calendar.domain.CalendarSchedule;
import org.one.calendar.dto.response.CalendarMonthlyResponseDto;
import org.one.calendar.dto.response.CalendarResponseDto;
import org.one.calendar.repository.CalendarScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorCalendarService {
    private final CalendarScheduleRepository calendarScheduleRepository;
    private static final DateTimeFormatter YEAR_MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

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

    /**
     * 특정 연도의 캘린더 일정을 월별로 그룹핑하여 조회합니다.
     *
     * @param year 연도
     * @return 월별 그룹핑된 일정 목록
     */
    @Transactional(readOnly = true)
    public List<CalendarMonthlyResponseDto> getYearScheduleList(int year) {
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
}
