package org.one.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.one.auth.domain.Admin;
import org.one.auth.repository.AdminRepository;
import org.one.auth.dto.request.LoginRequestDto;
import org.one.auth.dto.response.LoginResponseDto;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.global.security.JwtTokenProvider;
import org.one.global.security.RefreshToken;
import org.one.global.security.RefreshTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 로그인, 토큰 재발급, 로그아웃 처리를 담당합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminAuthService {

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	/**
	 * 관리자 계정 정보를 검증하고 Access Token과 Refresh Token을 발급합니다.
	 * 단일 세션 정책이므로 동일 사용자 row가 있으면 그 row를 갱신합니다.
	 *
	 * @param request 로그인 요청 정보
	 * @return 발급된 토큰 응답
	 */
	public LoginResponseDto login(LoginRequestDto request) {
		Admin admin = adminRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

		if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
			throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
		}

		String access = jwtTokenProvider.createAccessToken(String.valueOf(admin.getAdminId()), "ADMIN");
		String refresh = issueRefreshToken(admin.getAdminId());

		return LoginResponseDto.from(access, refresh);
	}

	/**
	 * 저장된 Refresh Token을 검증한 뒤 같은 row를 갱신하고 새 토큰 쌍을 발급합니다.
	 *
	 * @param refreshTokenStr 클라이언트가 전달한 Refresh Token 원문
	 * @return 새로 발급된 토큰 응답
	 */
	public LoginResponseDto refresh(String refreshTokenStr) {
		String tokenHash = hashToken(refreshTokenStr);
		RefreshToken stored = refreshTokenRepository.findByTokenHash(tokenHash)
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_TOKEN));

		if (stored.isRevoked() || stored.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}

		String newAccess = jwtTokenProvider.createAccessToken(String.valueOf(stored.getUserId()), "ADMIN");
		String newRefresh = rotateRefreshToken(stored);

		return LoginResponseDto.from(newAccess, newRefresh);
	}

	/**
	 * 전달받은 Refresh Token이 저장되어 있으면 폐기 처리합니다.
	 *
	 * @param refreshTokenStr 클라이언트가 전달한 Refresh Token 원문
	 */
	public void logout(String refreshTokenStr) {
		String tokenHash = hashToken(refreshTokenStr);
		refreshTokenRepository.findByTokenHash(tokenHash)
				.ifPresent(r -> {
					r.revoke();
					refreshTokenRepository.save(r);
				});
	}

	/**
	 * 랜덤 Refresh Token을 생성하고 해시 값과 만료 시각을 기존 row에 반영합니다.
	 * 이미 사용자 row가 있으면 같은 row를 덮어써서 단일 세션을 유지합니다.
	 *
	 * @param adminId 토큰을 발급받는 관리자 ID
	 * @return 클라이언트에 전달할 Refresh Token 원문
	 */
	private String issueRefreshToken(Long adminId) {
		return rotateRefreshToken(adminId, null);
	}

	/**
	 * 기존 Refresh Token row를 새 값으로 갱신합니다.
	 *
	 * @param existing 기존 Refresh Token 엔티티
	 * @return 새로 발급한 Refresh Token 원문
	 */
	private String rotateRefreshToken(RefreshToken existing) {
		return rotateRefreshToken(existing.getUserId(), existing);
	}

	/**
	 * 사용자 기준으로 Refresh Token row를 생성하거나 갱신합니다.
	 *
	 * @param adminId 관리자 ID
	 * @param existing 이미 조회한 엔티티가 있으면 그것을 사용하고, 없으면 새 row를 생성합니다.
	 * @return 새로 발급한 Refresh Token 원문
	 */
	private String rotateRefreshToken(Long adminId, RefreshToken existing) {
		String refreshToken = generateRefreshTokenValue();
		long refreshMillis = jwtTokenProvider.getRefreshExpirationMillis();
		LocalDateTime expiresAt = LocalDateTime.now().plus(Duration.ofMillis(refreshMillis));
		String tokenHash = hashToken(refreshToken);
		RefreshToken entity = existing != null
				? existing
				: refreshTokenRepository.findByUserId(adminId)
						.orElseGet(() -> new RefreshToken(adminId, tokenHash, expiresAt));
		entity.rotate(tokenHash, expiresAt);
		refreshTokenRepository.save(entity);
		return refreshToken;
	}

	/**
	 * URL 안전 문자로 구성된 Refresh Token 값을 생성합니다.
	 *
	 * @return 랜덤 Refresh Token 문자열
	 */
	private String generateRefreshTokenValue() {
		byte[] bytes = new byte[32];
		SECURE_RANDOM.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}

	/**
	 * Refresh Token 원문을 저장하지 않기 위해 SHA-256 해시로 변환합니다.
	 *
	 * @param token Refresh Token 원문
	 * @return 16진수 해시 문자열
	 */
	private String hashToken(String token) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashed = digest.digest(token.getBytes(StandardCharsets.UTF_8));
			StringBuilder builder = new StringBuilder(hashed.length * 2);
			for (byte value : hashed) {
				builder.append(String.format("%02x", value));
			}
			return builder.toString();
		} catch (Exception exception) {
			throw new IllegalStateException("refresh token hash generation failed", exception);
		}
	}
}
