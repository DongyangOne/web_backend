package org.one.domain.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_photo")
public class ProjectPhoto {

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

	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	protected ProjectPhoto() {}

	public ProjectPhoto(ProjectEvent projectEvent, String photoUrl, Integer priority) {
		this.projectEvent = projectEvent;
		this.photoUrl = photoUrl;
		this.priority = priority;
	}

	public Long getPhotoId() { return photoId; }
	public ProjectEvent getProjectEvent() { return projectEvent; }
	public String getPhotoUrl() { return photoUrl; }
	public Integer getPriority() { return priority; }
	public LocalDateTime getCreatedAt() { return createdAt; }
}
