package org.one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.one.global.entity.BaseEntity;

/**
 * 메인 페이지에 노출되는 주요활동 카드 엔티티입니다.
 */
@Entity
@Table(name = "activity_card")
public class ActivityCard extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cardId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_id")
	private MainPageConfig mainPageConfig;

	@Column(length = 100, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Column(nullable = false)
	private int cardOrder;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected ActivityCard() {}

	/**
	 * 카드 ID를 반환합니다.
	 *
	 * @return 카드 ID
	 */
	public Long getCardId() { return cardId; }

	/**
	 * 카드 제목을 반환합니다.
	 *
	 * @return 카드 제목
	 */
	public String getTitle() { return title; }

	/**
	 * 카드 내용을 반환합니다.
	 *
	 * @return 카드 내용
	 */
	public String getContent() { return content; }

	/**
	 * 카드 노출 순서를 반환합니다.
	 *
	 * @return 카드 노출 순서
	 */
	public int getCardOrder() { return cardOrder; }

	/**
	 * 카드 제목과 내용을 수정합니다.
	 *
	 * @param title 카드 제목
	 * @param content 카드 내용
	 */
	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
