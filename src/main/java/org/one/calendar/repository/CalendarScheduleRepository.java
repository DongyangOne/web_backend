package org.one.calendar.repository;

import java.time.LocalDate;
import java.util.List;
import org.one.calendar.domain.CalendarSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 캘린더 일정 조회를 담당하는 JPA Repository입니다.
 */
public interface CalendarScheduleRepository extends JpaRepository<CalendarSchedule, Long> {

	/**
	 * 지정한 ID 목록에 해당하는 일정 수를 반환합니다.
	 *
	 * @param calendarIds 일정 ID 목록
	 * @return 존재하는 일정 수
	 */
	long countByCalendarIdIn(List<Long> calendarIds);

	/**
	 * 지정한 기간과 겹치는 캘린더 일정을 시작일 오름차순으로 조회합니다.
	 *
	 * @param startDate 조회 시작일
	 * @param endDate 조회 종료일
	 * @return 기간과 겹치는 일정 목록
	 */
	@Query("SELECT c FROM CalendarSchedule c WHERE c.startDate <= :endDate AND c.endDate >= :startDate ORDER BY c.startDate ASC")
	List<CalendarSchedule> findByDateRange(
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate
	);
}
