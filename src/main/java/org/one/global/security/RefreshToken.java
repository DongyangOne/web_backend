package org.one.global.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import org.one.global.entity.BaseEntity;

/**
 * 관리자 Refresh Token의 해시 값과 만료/폐기 상태를 저장하는 엔티티입니다.
 */
@Entity
@Table(
		name = "refresh_token",
		uniqueConstraints = @UniqueConstraint(name = "uk_refresh_token_user_id", columnNames = "user_id")
)
public class RefreshToken extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "token_hash", nullable = false, unique = true, length = 128)
	private String tokenHash;

	@Column(nullable = false)
	private LocalDateTime expiresAt;

	@Column(nullable = false)
	private boolean revoked = false;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected RefreshToken() {}

	/**
	 * 새 Refresh Token 저장 엔티티를 생성합니다.
	 *
	 * @param userId 토큰 소유 관리자 ID
	 * @param tokenHash Refresh Token 원문의 SHA-256 해시
	 * @param expiresAt 토큰 만료 시각
	 */
	public RefreshToken(Long userId, String tokenHash, LocalDateTime expiresAt) {
		this.userId = userId;
		this.tokenHash = tokenHash;
		this.expiresAt = expiresAt;
		this.revoked = false;
	}

	/**
	 * Refresh Token 엔티티 ID를 반환합니다.
	 *
	 * @return 엔티티 ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 토큰 소유 관리자 ID를 반환합니다.
	 *
	 * @return 관리자 ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Refresh Token 해시 값을 반환합니다.
	 *
	 * @return 토큰 해시
	 */
	public String getTokenHash() {
		return tokenHash;
	}

	/**
	 * Refresh Token 만료 시각을 반환합니다.
	 *
	 * @return 만료 시각
	 */
	public LocalDateTime getExpiresAt() {
		return expiresAt;
	}

	/**
	 * Refresh Token 폐기 여부를 반환합니다.
	 *
	 * @return 폐기되었으면 true
	 */
	public boolean isRevoked() {
		return revoked;
	}

	/**
	 * Refresh Token을 폐기 상태로 변경합니다.
	 */
	public void revoke() {
		this.revoked = true;
	}

	/**
	 * 기존 엔티티에 새 Refresh Token 해시와 만료 시각을 반영합니다.
	 *
	 * @param newTokenHash 새 Refresh Token 해시
	 * @param newExpiresAt 새 만료 시각
	 */
	public void rotate(String newTokenHash, LocalDateTime newExpiresAt) {
		this.tokenHash = newTokenHash;
		this.expiresAt = newExpiresAt;
		this.revoked = false;
	}
}
