 package org.one.domain.calendar.repository;

import java.time.LocalDate;
import java.util.List;
import org.one.domain.calendar.entity.CalendarSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalendarScheduleRepository extends JpaRepository<CalendarSchedule, Long> {

	@Query("SELECT c FROM CalendarSchedule c WHERE c.startDate <= :endDate AND c.endDate >= :startDate ORDER BY c.startDate ASC")
	List<CalendarSchedule> findByDateRange(
			@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate
	);
}
