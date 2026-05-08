package org.one.domain.project.entity;

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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.one.domain.mainpage.entity.MainPageConfig;

@Entity
@Table(name = "project_event")
public class ProjectEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long projectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "config_id")
	private MainPageConfig mainPageConfig;

	@Column(nullable = false, length = 100)
	private String projectName;

	@Column(nullable = false)
	private Integer participantCount;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false)
	private Integer priority;

	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "projectEvent", fetch = FetchType.LAZY)
	@OrderBy("priority ASC")
	private List<ProjectPhoto> photos = new ArrayList<>();

	protected ProjectEvent() {}

	public ProjectEvent(MainPageConfig mainPageConfig, String projectName,
			Integer participantCount, String description, Integer priority) {
		this.mainPageConfig = mainPageConfig;
		this.projectName = projectName;
		this.participantCount = participantCount;
		this.description = description;
		this.priority = priority;
	}

	public Long getProjectId() { return projectId; }
	public MainPageConfig getMainPageConfig() { return mainPageConfig; }
	public String getProjectName() { return projectName; }
	public Integer getParticipantCount() { return participantCount; }
	public String getDescription() { return description; }
	public Integer getPriority() { return priority; }
	public LocalDateTime getCreatedAt() { return createdAt; }
	public List<ProjectPhoto> getPhotos() { return photos; }

	public void update(String projectName, Integer participantCount,
			String description, Integer priority) {
		this.projectName = projectName;
		this.participantCount = participantCount;
		this.description = description;
		this.priority = priority;
	}
}
