package org.one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.one.global.entity.BaseEntity;

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

	@Column(length = 255)
	private String description;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected CalendarSchedule() {}

	/**
	 * 캘린더 일정 엔티티를 생성합니다.
	 *
	 * @param title 일정 제목
	 * @param startDate 일정 시작일
	 * @param endDate 일정 종료일
	 * @param description 일정 설명
	 */
	public CalendarSchedule(String title, LocalDate startDate, LocalDate endDate, String description) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
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
	 * 일정 설명을 반환합니다.
	 *
	 * @return 일정 설명
	 */
	public String getDescription() { return description; }

	/**
	 * 캘린더 일정 내용을 수정합니다.
	 *
	 * @param title 일정 제목
	 * @param startDate 일정 시작일
	 * @param endDate 일정 종료일
	 * @param description 일정 설명
	 */
	public void update(String title, LocalDate startDate, LocalDate endDate, String description) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}
}
