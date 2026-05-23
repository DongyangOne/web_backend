package org.one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.one.global.entity.BaseEntity;

/**
 * 메인 페이지의 로고 설정을 저장하는 싱글톤 엔티티입니다.
 */
@Entity
@Table(name = "main_page")
public class MainPageConfig extends BaseEntity {

	@Id
	private Integer mainId;

	@Column(length = 500)
	private String logoUrl;

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
	 * 메인 페이지 로고 URL을 수정합니다.
	 *
	 * @param logoUrl 변경할 로고 URL
	 */
	public void updateLogo(String logoUrl) {
		this.logoUrl = logoUrl;
	}
}
