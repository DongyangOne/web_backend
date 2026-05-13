package org.one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.one.global.entity.BaseEntity;

/**
 * 메인 페이지의 로고, 소개, 모집 기간 설정을 저장하는 싱글톤 엔티티입니다.
 */
@Entity
@Table(name = "main_page")
public class MainPageConfig extends BaseEntity {

	@Id
	private Integer mainId;

	@Column(length = 500)
	private String logoUrl;

	@Column(columnDefinition = "TEXT")
	private String description;

	private LocalDate recruitmentStart;

	private LocalDate recruitmentEnd;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected MainPageConfig() {}

	/**
	 * main_id가 1인 메인 페이지 기본 설정 엔티티를 생성합니다.
	 *
	 * @return 기본 메인 페이지 설정
	 */
	public static MainPageConfig singleton() {
		MainPageConfig config = new MainPageConfig();
		config.mainId = 1;
		return config;
	}

	/**
	 * 메인 페이지 설정 ID를 반환합니다.
	 *
	 * @return 설정 ID
	 */
	public Integer getMainId() { return mainId; }

	/**
	 * 메인 페이지 로고 URL을 반환합니다.
	 *
	 * @return 로고 URL
	 */
	public String getLogoUrl() { return logoUrl; }

	/**
	 * 메인 페이지 소개 문구를 반환합니다.
	 *
	 * @return 소개 문구
	 */
	public String getDescription() { return description; }

	/**
	 * 모집 시작일을 반환합니다.
	 *
	 * @return 모집 시작일
	 */
	public LocalDate getRecruitmentStart() { return recruitmentStart; }

	/**
	 * 모집 종료일을 반환합니다.
	 *
	 * @return 모집 종료일
	 */
	public LocalDate getRecruitmentEnd() { return recruitmentEnd; }

	/**
	 * 메인 페이지 설정 값을 수정합니다.
	 *
	 * @param logoUrl 로고 URL
	 * @param description 소개 문구
	 * @param recruitmentStart 모집 시작일
	 * @param recruitmentEnd 모집 종료일
	 */
	public void update(String logoUrl, String description,
			LocalDate recruitmentStart, LocalDate recruitmentEnd) {
		this.logoUrl = logoUrl;
		this.description = description;
		this.recruitmentStart = recruitmentStart;
		this.recruitmentEnd = recruitmentEnd;
	}
}
