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

/**
 * 프로젝트에 사용된 기술 스택 정보를 저장하는 엔티티입니다.
 */
@Entity
@Table(name = "project_tech_stack")
public class ProjectTechStack {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long techStackId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private ProjectEvent projectEvent;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String name;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected ProjectTechStack() {}

	/**
	 * 기술 스택 엔티티를 생성합니다.
	 *
	 * @param projectEvent 연결된 프로젝트 행사
	 * @param name 기술 스택명
	 */
	public ProjectTechStack(ProjectEvent projectEvent, String name) {
		this.projectEvent = projectEvent;
		this.name = name;
	}

	/**
	 * 기술 스택 ID를 반환합니다.
	 *
	 * @return 기술 스택 ID
	 */
	public Long getTechStackId() { return techStackId; }

	/**
	 * 기술 스택명을 반환합니다.
	 *
	 * @return 기술 스택명
	 */
	public String getName() { return name; }
}
