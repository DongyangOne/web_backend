package org.one.domain.calendar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "calendar_schedule")
public class CalendarSchedule {

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

	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	protected CalendarSchedule() {}

	public CalendarSchedule(String title, LocalDate startDate, LocalDate endDate, String description) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}

	public Long getCalendarId() { return calendarId; }
	public String getTitle() { return title; }
	public LocalDate getStartDate() { return startDate; }
	public LocalDate getEndDate() { return endDate; }
	public String getDescription() { return description; }
	public LocalDateTime getCreatedAt() { return createdAt; }

	public void update(String title, LocalDate startDate, LocalDate endDate, String description) {
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.description = description;
	}
}
