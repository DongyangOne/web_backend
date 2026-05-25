package org.one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.one.global.entity.BaseEntity;

/**
 * 모집 공고 설정을 저장하는 싱글톤 엔티티입니다.
 */
@Entity
@Table(name = "recruitment")
public class Recruitment extends BaseEntity {

	@Id
	private Integer recruitmentId;

	@Column(length = 255)
	private String target;

	@Column(columnDefinition = "TEXT")
	private String field;

	private LocalDate recruitmentStart;

	private LocalDate recruitmentEnd;

	private LocalDate interviewStart;

	private LocalDate interviewEnd;

	private LocalDate notificationDate;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected Recruitment() {}

	/**
	 * recruitment_id가 1인 기본 모집 공고 엔티티를 생성합니다.
	 *
	 * @return 기본 모집 공고 설정
	 */
	public static Recruitment singleton() {
		Recruitment recruitment = new Recruitment();
		recruitment.recruitmentId = 1;
		return recruitment;
	}

	/**
	 * 모집 공고 ID를 반환합니다.
	 *
	 * @return 모집 공고 ID
	 */
	public Integer getRecruitmentId() { return recruitmentId; }

	/**
	 * 모집 대상을 반환합니다.
	 *
	 * @return 모집 대상
	 */
	public String getTarget() { return target; }

	/**
	 * 지원 분야를 반환합니다.
	 *
	 * @return 지원 분야
	 */
	public String getField() { return field; }

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
	 * 면접 시작일을 반환합니다.
	 *
	 * @return 면접 시작일
	 */
	public LocalDate getInterviewStart() { return interviewStart; }

	/**
	 * 면접 종료일을 반환합니다.
	 *
	 * @return 면접 종료일
	 */
	public LocalDate getInterviewEnd() { return interviewEnd; }

	/**
	 * 합격자 발표일을 반환합니다.
	 *
	 * @return 합격자 발표일
	 */
	public LocalDate getNotificationDate() { return notificationDate; }

	/**
	 * 모집 공고 정보를 수정합니다.
	 *
	 * @param target 모집 대상
	 * @param field 지원 분야
	 * @param recruitmentStart 모집 시작일
	 * @param recruitmentEnd 모집 종료일
	 * @param interviewStart 면접 시작일
	 * @param interviewEnd 면접 종료일
	 * @param notificationDate 합격자 발표일
	 */
	public void update(String target, String field,
			LocalDate recruitmentStart, LocalDate recruitmentEnd,
			LocalDate interviewStart, LocalDate interviewEnd,
			LocalDate notificationDate) {
		this.field = field;
		this.target = target;
		this.recruitmentStart = recruitmentStart;
		this.recruitmentEnd = recruitmentEnd;
		this.interviewStart = interviewStart;
		this.interviewEnd = interviewEnd;
		this.notificationDate = notificationDate;
	}
}
