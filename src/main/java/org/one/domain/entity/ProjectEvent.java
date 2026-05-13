package org.one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import org.one.global.entity.BaseEntity;

/**
 * 메인 페이지에 노출할 프로젝트 행사 정보를 저장하는 엔티티입니다.
 */
@Entity
@Table(name = "project_event")
public class ProjectEvent extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "main_id")
	private MainPageConfig mainPageConfig;

	@Column(nullable = false, length = 100)
	private String projectName;

	@Column(nullable = false)
	private Integer participantCount;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private Integer priority;

	@OneToMany(mappedBy = "projectEvent", fetch = FetchType.LAZY)
	@OrderBy("priority ASC")
	private List<ProjectPhoto> photos = new ArrayList<>();

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected ProjectEvent() {}

	/**
	 * 프로젝트 행사 엔티티를 생성합니다.
	 *
	 * @param mainPageConfig 연결된 메인 페이지 설정
	 * @param projectName 프로젝트명
	 * @param participantCount 참여 인원
	 * @param description 프로젝트 설명
	 * @param priority 노출 순서
	 */
	public ProjectEvent(MainPageConfig mainPageConfig, String projectName,
			Integer participantCount, String description, Integer priority) {
		this.mainPageConfig = mainPageConfig;
		this.projectName = projectName;
		this.participantCount = participantCount;
		this.description = description;
		this.priority = priority;
	}

	/**
	 * 프로젝트 ID를 반환합니다.
	 *
	 * @return 프로젝트 ID
	 */
	public Long getProjectId() { return projectId; }

	/**
	 * 연결된 메인 페이지 설정을 반환합니다.
	 *
	 * @return 메인 페이지 설정
	 */
	public MainPageConfig getMainPageConfig() { return mainPageConfig; }

	/**
	 * 프로젝트명을 반환합니다.
	 *
	 * @return 프로젝트명
	 */
	public String getProjectName() { return projectName; }

	/**
	 * 참여 인원을 반환합니다.
	 *
	 * @return 참여 인원
	 */
	public Integer getParticipantCount() { return participantCount; }

	/**
	 * 프로젝트 설명을 반환합니다.
	 *
	 * @return 프로젝트 설명
	 */
	public String getDescription() { return description; }

	/**
	 * 노출 순서를 반환합니다.
	 *
	 * @return 노출 순서
	 */
	public Integer getPriority() { return priority; }

	/**
	 * 프로젝트 사진 목록을 노출 순서대로 반환합니다.
	 *
	 * @return 프로젝트 사진 목록
	 */
	public List<ProjectPhoto> getPhotos() { return photos; }

	/**
	 * 프로젝트 행사 정보를 수정합니다.
	 *
	 * @param projectName 프로젝트명
	 * @param participantCount 참여 인원
	 * @param description 프로젝트 설명
	 * @param priority 노출 순서
	 */
	public void update(String projectName, Integer participantCount,
			String description, Integer priority) {
		this.projectName = projectName;
		this.participantCount = participantCount;
		this.description = description;
		this.priority = priority;
	}
}
