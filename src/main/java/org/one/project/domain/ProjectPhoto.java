package org.one.project.domain;

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
 * 프로젝트 행사에 연결된 사진 URL과 노출 순서를 저장하는 엔티티입니다.
 */
@Entity
@Table(name = "project_photo")
public class ProjectPhoto extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long photoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private ProjectEvent projectEvent;

	@Column(nullable = false, length = 500)
	private String photoUrl;

	@Column(nullable = false)
	private Integer priority;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected ProjectPhoto() {}

	/**
	 * 프로젝트 사진 엔티티를 생성합니다.
	 *
	 * @param projectEvent 연결된 프로젝트 행사
	 * @param photoUrl 사진 URL
	 * @param priority 노출 순서
	 */
	public ProjectPhoto(ProjectEvent projectEvent, String photoUrl, Integer priority) {
		this.projectEvent = projectEvent;
		this.photoUrl = photoUrl;
		this.priority = priority;
	}

	/**
	 * 사진 ID를 반환합니다.
	 *
	 * @return 사진 ID
	 */
	public Long getPhotoId() { return photoId; }

	/**
	 * 연결된 프로젝트 행사를 반환합니다.
	 *
	 * @return 프로젝트 행사
	 */
	public ProjectEvent getProjectEvent() { return projectEvent; }

	/**
	 * 사진 URL을 반환합니다.
	 *
	 * @return 사진 URL
	 */
	public String getPhotoUrl() { return photoUrl; }

	/**
	 * 사진 노출 순서를 반환합니다.
	 *
	 * @return 노출 순서
	 */
	public Integer getPriority() { return priority; }

}
