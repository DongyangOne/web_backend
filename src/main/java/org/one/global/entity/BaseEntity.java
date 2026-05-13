package org.one.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 생성/수정 시각을 공통으로 관리하는 JPA 상위 엔티티입니다.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	/**
	 * 엔티티 생성 시각을 반환합니다.
	 *
	 * @return 생성 시각
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * 엔티티 마지막 수정 시각을 반환합니다.
	 *
	 * @return 수정 시각
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
}
