package org.one.domain.entity;

import jakarta.persistence.CascadeType;
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
import java.time.LocalDate;
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

	@Column(columnDefinition = "TEXT")
	private String year;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String projectName;

	@Column(columnDefinition = "TEXT")
	private String award;

	@Column(columnDefinition = "TEXT")
	private String activity;

	private LocalDate startDate;

	private LocalDate endDate;

	@Column(nullable = false)
	private Integer participantCount;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private Integer priority;

	@OneToMany(mappedBy = "projectEvent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("priority ASC")
	private List<ProjectPhoto> photos = new ArrayList<>();

	@OneToMany(mappedBy = "projectEvent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProjectTechStack> techStacks = new ArrayList<>();

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected ProjectEvent() {}

	/**
	 * 프로젝트 행사 엔티티를 생성합니다.
	 *
	 * @param mainPageConfig 연결된 메인 페이지 설정
	 * @param year 년도
	 * @param projectName 프로젝트명
	 * @param award 수상 내역
	 * @param activity 해당 연도 활동 내역 (예: "2023 하계 MT · 정기 세미나 및 튜터링 운영")
	 * @param startDate 시작일
	 * @param endDate 종료일
	 * @param participantCount 참여 인원
	 * @param description 프로젝트 설명
	 * @param priority 노출 순서 (년도 기반 정렬)
	 */
	public ProjectEvent(MainPageConfig mainPageConfig, String year, String projectName,
			String award, String activity, LocalDate startDate, LocalDate endDate,
			Integer participantCount, String description, Integer priority) {
		this.mainPageConfig = mainPageConfig;
		this.year = year;
		this.projectName = projectName;
		this.award = award;
		this.activity = activity;
		this.startDate = startDate;
		this.endDate = endDate;
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
	 * 년도를 반환합니다.
	 *
	 * @return 년도
	 */
	public String getYear() { return year; }

	/**
	 * 프로젝트명을 반환합니다.
	 *
	 * @return 프로젝트명
	 */
	public String getProjectName() { return projectName; }

	/**
	 * 수상 내역을 반환합니다.
	 *
	 * @return 수상 내역
	 */
	public String getAward() { return award; }

	/**
	 * 해당 연도 활동 내역을 반환합니다.
	 *
	 * @return 활동 내역
	 */
	public String getActivity() { return activity; }

	/**
	 * 프로젝트 시작일을 반환합니다.
	 *
	 * @return 시작일
	 */
	public LocalDate getStartDate() { return startDate; }

	/**
	 * 프로젝트 종료일을 반환합니다.
	 *
	 * @return 종료일
	 */
	public LocalDate getEndDate() { return endDate; }

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
	 * 프로젝트 사진 목록을 반환합니다.
	 *
	 * @return 프로젝트 사진 목록
	 */
	public List<ProjectPhoto> getPhotos() { return photos; }

	/**
	 * 기술 스택 목록을 반환합니다.
	 *
	 * @return 기술 스택 목록
	 */
	public List<ProjectTechStack> getTechStacks() { return techStacks; }

	/**
	 * 프로젝트 행사 정보를 수정합니다.
	 *
	 * @param year 년도
	 * @param projectName 프로젝트명
	 * @param award 수상 내역
	 * @param activity 해당 연도 활동 내역
	 * @param startDate 시작일
	 * @param endDate 종료일
	 * @param participantCount 참여 인원
	 * @param description 프로젝트 설명
	 */
	public void update(String year, String projectName, String award, String activity,
			LocalDate startDate, LocalDate endDate,
			Integer participantCount, String description) {
		this.year = year;
		this.projectName = projectName;
		this.award = award;
		this.activity = activity;
		this.startDate = startDate;
		this.endDate = endDate;
		this.participantCount = participantCount;
		this.description = description;
	}
}
