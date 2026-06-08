package org.one.global.security;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Refresh Token 엔티티 조회와 갱신을 담당하는 JPA Repository입니다.
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	/**
	 * Refresh Token 원문의 해시 값으로 저장된 토큰을 조회합니다.
	 *
	 * @param tokenHash Refresh Token SHA-256 해시
	 * @return 저장된 Refresh Token
	 */
	Optional<RefreshToken> findByTokenHash(String tokenHash);

	/**
	 * 특정 관리자에게 발급된 Refresh Token row를 조회합니다.
	 *
	 * @param userId 관리자 ID
	 * @return 관리자별 Refresh Token
	 */
	Optional<RefreshToken> findByUserId(Long userId);
}
