package org.one.domain.mainpage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "main_page")
public class MainPageConfig {

	@Id
	private Integer mainId;

	@Column(length = 500)
	private String logoUrl;

	@Column(columnDefinition = "TEXT")
	private String description;

	private LocalDate recruitmentStart;

	private LocalDate recruitmentEnd;

	@Column(insertable = false, updatable = false)
	private LocalDateTime updatedAt;

	protected MainPageConfig() {}

	public static MainPageConfig singleton() {
		MainPageConfig config = new MainPageConfig();
		config.mainId = 1;
		return config;
	}

	public Integer getMainId() { return mainId; }
	public String getLogoUrl() { return logoUrl; }
	public String getDescription() { return description; }
	public LocalDate getRecruitmentStart() { return recruitmentStart; }
	public LocalDate getRecruitmentEnd() { return recruitmentEnd; }
	public LocalDateTime getUpdatedAt() { return updatedAt; }

	public void update(String logoUrl, String description,
			LocalDate recruitmentStart, LocalDate recruitmentEnd) {
		this.logoUrl = logoUrl;
		this.description = description;
		this.recruitmentStart = recruitmentStart;
		this.recruitmentEnd = recruitmentEnd;
	}
}
