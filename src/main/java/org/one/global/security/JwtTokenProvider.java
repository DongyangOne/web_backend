package org.one.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.one.global.config.auth.JwtProperties;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * JWT 발급, 검증, Claims 파싱, Spring Security 인증 객체 생성을 담당합니다.
 */
@Component
public class JwtTokenProvider {

	private final SecretKey secretKey;
	private final long accessExpirationMillis;
	private final long refreshExpirationMillis;

	/**
	 * JWT 설정값으로 서명 키와 토큰 만료 시간을 초기화합니다.
	 *
	 * @param jwtProperties JWT 설정 프로퍼티
	 */
	public JwtTokenProvider(JwtProperties jwtProperties) {
		this.secretKey = Keys.hmacShaKeyFor(resolveKeyBytes(jwtProperties.getSecret()));
		this.accessExpirationMillis = jwtProperties.getAccessExpiration();
		this.refreshExpirationMillis = jwtProperties.getRefreshExpiration();
	}

	/**
	 * 관리자 인증에 사용할 Access Token을 생성합니다.
	 *
	 * @param subject 토큰 주체, 현재는 관리자 ID 문자열
	 * @param role 관리자 권한명
	 * @return JWT Access Token
	 */
	public String createAccessToken(String subject, String role) {
		return createToken(subject, role, "ACCESS", accessExpirationMillis);
	}

	/**
	 * JWT 형식의 Refresh Token을 생성합니다.
	 *
	 * @param subject 토큰 주체, 현재는 관리자 ID 문자열
	 * @param role 관리자 권한명
	 * @return JWT Refresh Token
	 */
	public String createRefreshToken(String subject, String role) {
		return createToken(subject, role, "REFRESH", refreshExpirationMillis);
	}

	/**
	 * 유효한 Access Token에서 Spring Security Authentication 객체를 생성합니다.
	 *
	 * @param token Bearer 접두사를 제거한 JWT 문자열
	 * @return 인증 컨텍스트에 저장할 Authentication
	 */
	public Authentication getAuthentication(String token) {
		Claims claims = getAccessClaims(token);
		String role = getRequiredRole(claims);
		Long userId;
		try {
			userId = Long.valueOf(claims.getSubject());
		} catch (NumberFormatException exception) {
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}

		return new UsernamePasswordAuthenticationToken(
				userId,
				token,
				List.of(new SimpleGrantedAuthority("ROLE_" + role))
		);
	}

	/**
	 * 토큰의 subject 값을 반환합니다.
	 *
	 * @param token JWT 문자열
	 * @return 토큰 subject
	 */
	public String getSubject(String token) {
		try {
			return getClaims(token).getSubject();
		} catch (Exception exception) {
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}
	}

	/**
	 * 토큰의 role 클레임을 반환하며, 비어 있으면 유효하지 않은 토큰으로 처리합니다.
	 *
	 * @param token JWT 문자열
	 * @return role 클레임 값
	 */
	public String getRole(String token) {
		try {
			return getRequiredRole(getClaims(token));
		} catch (BusinessException exception) {
			throw exception;
		} catch (Exception exception) {
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}
	}

	/**
	 * Claims를 한 번만 파싱해 Access Token 여부를 확인합니다.
	 *
	 * @param token JWT 문자열
	 * @return Access Token이면 true
	 */
	public boolean isAccessToken(String token) {
		try {
			Claims claims = getClaims(token);
			return "ACCESS".equals(claims.get("tokenType", String.class));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 토큰 서명과 만료 시간을 검증합니다.
	 *
	 * @param token JWT 문자열
	 * @return 유효하면 true
	 */
	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Refresh Token 저장 만료 시간을 계산하기 위한 만료 밀리초를 반환합니다.
	 *
	 * @return Refresh Token 만료 시간 밀리초
	 */
	public long getRefreshExpirationMillis() {
		return refreshExpirationMillis;
	}

	/**
	 * 공통 Claims를 담은 JWT를 생성합니다.
	 *
	 * @param subject 토큰 주체
	 * @param role 권한명
	 * @param tokenType ACCESS 또는 REFRESH
	 * @param expirationMillis 만료 시간 밀리초
	 * @return JWT 문자열
	 */
	private String createToken(String subject, String role, String tokenType, long expirationMillis) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMillis);
		return Jwts.builder()
				.subject(subject)
				.claim("role", role)
				.claim("tokenType", tokenType)
				.issuedAt(now)
				.expiration(expiry)
				.signWith(secretKey)
				.compact();
	}

	/**
	 * Access Token인지 확인한 뒤 Claims를 반환합니다.
	 *
	 * @param token JWT 문자열
	 * @return Access Token Claims
	 */
	private Claims getAccessClaims(String token) {
		Claims claims;
		try {
			claims = getClaims(token);
		} catch (Exception exception) {
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}
		if (!"ACCESS".equals(claims.get("tokenType", String.class))) {
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}
		return claims;
	}

	/**
	 * role 클레임을 필수 값으로 읽습니다.
	 *
	 * @param claims JWT Claims
	 * @return role 클레임 값
	 */
	private String getRequiredRole(Claims claims) {
		Object role = claims.get("role");
		if (role == null || role.toString().isBlank()) {
			throw new BusinessException(ErrorCode.INVALID_TOKEN);
		}
		return role.toString().trim();
	}

	/**
	 * JWT 문자열을 파싱해 Claims를 반환합니다.
	 *
	 * @param token JWT 문자열
	 * @return JWT Claims
	 */
	private Claims getClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	/**
	 * HMAC SHA 서명에 필요한 최소 길이를 맞춰 secret 바이트를 반환합니다.
	 *
	 * @param secret 설정 파일의 JWT secret
	 * @return 서명 키 바이트
	 */
	private byte[] resolveKeyBytes(String secret) {
		byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
		if (bytes.length >= 32) {
			return bytes;
		}
		byte[] padded = new byte[32];
		System.arraycopy(bytes, 0, padded, 0, bytes.length);
		return padded;
	}
}
