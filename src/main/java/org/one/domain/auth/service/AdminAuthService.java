package org.one.domain.auth.service;

import org.one.domain.admin.entity.Admin;
import org.one.domain.admin.repository.AdminRepository;
import org.one.domain.auth.dto.LoginRequest;
import org.one.global.apiPayload.code.ErrorCode;
import org.one.global.apiPayload.exception.BusinessException;
import org.one.global.security.jwt.JwtTokenProvider;
import org.one.global.security.jwt.RefreshTokenRepository;
import org.one.global.security.jwt.RefreshToken;
import java.time.LocalDateTime;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

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

	public org.one.domain.auth.dto.LoginResponse login(LoginRequest request) {
		Admin admin = adminRepository.findByUsername(request.username())
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

		if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
			throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
		}

		var adminId = String.valueOf(admin.getAdminId());
		String access = jwtTokenProvider.createAccessToken(adminId, "ADMIN");
		String refresh = jwtTokenProvider.createRefreshToken(adminId, "ADMIN");

		// persist refresh token
		long refreshMillis = jwtTokenProvider.getRefreshExpirationMillis();
		RefreshToken refreshToken = new RefreshToken(admin.getAdminId(), "ADMIN", refresh, LocalDateTime.now().plusNanos(refreshMillis * 1_000_000L));
		refreshTokenRepository.save(refreshToken);

		return org.one.domain.auth.dto.LoginResponse.of(access, refresh);
	}

	public org.one.domain.auth.dto.LoginResponse refresh(String refreshTokenStr) {
		if (!jwtTokenProvider.validateToken(refreshTokenStr) || !"REFRESH".equals(jwtTokenProvider.getTokenType(refreshTokenStr))) {
			throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
		}

		RefreshToken stored = refreshTokenRepository.findByToken(refreshTokenStr)
				.orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

		if (stored.isRevoked() || stored.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
		}

		var userId = String.valueOf(stored.getUserId());
		String newAccess = jwtTokenProvider.createAccessToken(userId, stored.getRole());
		String newRefresh = jwtTokenProvider.createRefreshToken(userId, stored.getRole());

		long refreshMillis = jwtTokenProvider.getRefreshExpirationMillis();
		stored.rotate(newRefresh, LocalDateTime.now().plusNanos(refreshMillis * 1_000_000L));
		refreshTokenRepository.save(stored);

		return org.one.domain.auth.dto.LoginResponse.of(newAccess, newRefresh);
	}

	public void logout(String refreshTokenStr) {
		refreshTokenRepository.findByToken(refreshTokenStr)
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
}
