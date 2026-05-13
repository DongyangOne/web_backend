package org.one.domain.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import org.one.domain.entity.Admin;
import org.one.domain.repository.AdminRepository;
import org.one.domain.dto.request.LoginRequest;
import org.one.domain.dto.response.LoginResponse;
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
public class AdminAuthService {

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	/**
	 * 관리자 인증 처리에 필요한 저장소, 암호화 도구, 토큰 컴포넌트를 주입받습니다.
	 *
	 * @param adminRepository 관리자 계정 저장소
	 * @param passwordEncoder 비밀번호 검증 도구
	 * @param jwtTokenProvider JWT 발급 컴포넌트
	 * @param refreshTokenRepository Refresh Token 저장소
	 */
	public AdminAuthService(AdminRepository adminRepository, PasswordEncoder passwordEncoder,
			JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	/**
	 * 관리자 계정 정보를 검증하고 Access Token과 Refresh Token을 발급합니다.
	 *
	 * @param request 로그인 요청 정보
	 * @return 발급된 토큰 응답
	 */
	public LoginResponse login(LoginRequest request) {
		Admin admin = adminRepository.findByUsername(request.username())
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

		if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
			throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
		}

		String access = jwtTokenProvider.createAccessToken(String.valueOf(admin.getAdminId()), "ADMIN");
		String refresh = issueRefreshToken(admin.getAdminId());

		return LoginResponse.of(access, refresh);
	}

	/**
	 * 저장된 Refresh Token을 검증한 뒤 기존 토큰을 폐기하고 새 토큰 쌍을 발급합니다.
	 *
	 * @param refreshTokenStr 클라이언트가 전달한 Refresh Token 원문
	 * @return 새로 발급된 토큰 응답
	 */
	public LoginResponse refresh(String refreshTokenStr) {
		String tokenHash = hashToken(refreshTokenStr);
		RefreshToken stored = refreshTokenRepository.findByTokenHash(tokenHash)
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_TOKEN));

		if (stored.isRevoked() || stored.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}

		stored.revoke();
		refreshTokenRepository.save(stored);

		String newAccess = jwtTokenProvider.createAccessToken(String.valueOf(stored.getUserId()), "ADMIN");
		String newRefresh = issueRefreshToken(stored.getUserId());

		return LoginResponse.of(newAccess, newRefresh);
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
	 * 랜덤 Refresh Token을 생성하고 해시 값과 만료 시각만 DB에 저장합니다.
	 *
	 * @param adminId 토큰을 발급받는 관리자 ID
	 * @return 클라이언트에 전달할 Refresh Token 원문
	 */
	private String issueRefreshToken(Long adminId) {
		String refreshToken = generateRefreshTokenValue();
		long refreshMillis = jwtTokenProvider.getRefreshExpirationMillis();
		RefreshToken entity = new RefreshToken(
				adminId,
				hashToken(refreshToken),
				LocalDateTime.now().plus(Duration.ofMillis(refreshMillis))
		);
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
