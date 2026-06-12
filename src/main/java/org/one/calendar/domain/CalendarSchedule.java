package org.one.calendar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.one.global.entity.BaseEntity;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;

/**
 * 캘린더에 노출할 기간형 일정을 저장하는 엔티티입니다.
 */
@Entity
@Table(name = "calendar_schedule")
public class CalendarSchedule extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long calendarId;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false)
	private LocalDate endDate;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected CalendarSchedule() {}

	/**
	 * 캘린더 일정 엔티티를 생성합니다.
	 * 시작일이 종료일보다 늦으면 {@link BusinessException}을 던집니다.
	 *
	 * @param title 일정 제목
	 * @param startDate 일정 시작일
	 * @param endDate 일정 종료일
	 */
	public CalendarSchedule(String title, LocalDate startDate, LocalDate endDate) {
		validateDateRange(startDate, endDate);
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * 캘린더 일정 ID를 반환합니다.
	 *
	 * @return 일정 ID
	 */
	public Long getCalendarId() { return calendarId; }

	/**
	 * 일정 제목을 반환합니다.
	 *
	 * @return 일정 제목
	 */
	public String getTitle() { return title; }

	/**
	 * 일정 시작일을 반환합니다.
	 *
	 * @return 시작일
	 */
	public LocalDate getStartDate() { return startDate; }

	/**
	 * 일정 종료일을 반환합니다.
	 *
	 * @return 종료일
	 */
	public LocalDate getEndDate() { return endDate; }

	/**
	 * 캘린더 일정 내용을 수정합니다.
	 * 시작일이 종료일보다 늦으면 {@link BusinessException}을 던집니다.
	 *
	 * @param title 일정 제목
	 * @param startDate 일정 시작일
	 * @param endDate 일정 종료일
	 */
	public void update(String title, LocalDate startDate, LocalDate endDate) {
		validateDateRange(startDate, endDate);
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	private void validateDateRange(LocalDate start, LocalDate end) {
		if (start != null && end != null && start.isAfter(end)) {
			throw new BusinessException(ErrorCode.INVALID_DATE_RANGE);
		}
	}
}
