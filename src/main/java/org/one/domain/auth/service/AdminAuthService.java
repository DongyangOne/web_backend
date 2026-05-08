package org.one.domain.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import org.one.domain.admin.entity.Admin;
import org.one.domain.admin.repository.AdminRepository;
import org.one.domain.auth.dto.LoginRequest;
import org.one.domain.auth.dto.LoginResponse;
import org.one.global.apiPayload.code.ErrorCode;
import org.one.global.apiPayload.exception.BusinessException;
import org.one.global.security.jwt.JwtTokenProvider;
import org.one.global.security.jwt.RefreshToken;
import org.one.global.security.jwt.RefreshTokenRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AdminAuthService {

	private static final SecureRandom SECURE_RANDOM = new SecureRandom();

	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	public AdminAuthService(AdminRepository adminRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
							 RefreshTokenRepository refreshTokenRepository) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public LoginResponse login(LoginRequest request) {
		Admin admin = adminRepository.findByUsername(request.username())
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

		if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
			throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
		}

		String access = jwtTokenProvider.createAccessToken(String.valueOf(admin.getAdminId()), "ADMIN");
		String refresh = issueRefreshToken(admin.getAdminId());

		return org.one.domain.auth.dto.LoginResponse.of(access, refresh);
	}

	public LoginResponse refresh(String refreshTokenStr) {
		String tokenHash = hashToken(refreshTokenStr);
		RefreshToken stored = refreshTokenRepository.findByTokenHash(tokenHash)
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

		if (stored.isRevoked() || stored.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
		}

		stored.revoke();
		refreshTokenRepository.save(stored);

		String newAccess = jwtTokenProvider.createAccessToken(String.valueOf(stored.getUserId()), "ADMIN");
		String newRefresh = issueRefreshToken(stored.getUserId());

		return org.one.domain.auth.dto.LoginResponse.of(newAccess, newRefresh);
	}

	public void logout(String refreshTokenStr) {
		String tokenHash = hashToken(refreshTokenStr);
		refreshTokenRepository.findByTokenHash(tokenHash)
				.ifPresentOrElse(
						r -> {
							r.revoke();
							refreshTokenRepository.save(r);
						},
						() -> {
							// Token not found - silently succeed per OAuth/JWT patterns
						}
				);
	}

		private String issueRefreshToken(Long adminId) {
			String refreshToken = generateRefreshTokenValue();
			long refreshMillis = jwtTokenProvider.getRefreshExpirationMillis();
			RefreshToken entity = new RefreshToken(adminId, hashToken(refreshToken), LocalDateTime.now().plusNanos(refreshMillis * 1_000_000L));
			refreshTokenRepository.save(entity);
			return refreshToken;
		}

		private String generateRefreshTokenValue() {
			byte[] bytes = new byte[32];
			SECURE_RANDOM.nextBytes(bytes);
			return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
		}

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
