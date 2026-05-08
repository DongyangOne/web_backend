package org.one.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.one.global.config.props.JwtProperties;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

	private final SecretKey secretKey;
	private final long accessExpirationMillis;
	private final long refreshExpirationMillis;

	public JwtTokenProvider(
			JwtProperties jwtProperties
	) {
		this.secretKey = Keys.hmacShaKeyFor(resolveKeyBytes(jwtProperties.getSecret()));
		this.accessExpirationMillis = jwtProperties.getAccessExpiration();
		this.refreshExpirationMillis = jwtProperties.getRefreshExpiration();
	}

	public String createAccessToken(String subject, String role) {
		return createToken(subject, role, "ACCESS", accessExpirationMillis);
	}

	public String createRefreshToken(String subject, String role) {
		return createToken(subject, role, "REFRESH", refreshExpirationMillis);
	}

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

	public String getSubject(String token) {
		return getClaims(token).getSubject();
	}

	public String getRole(String token) {
		Object role = getClaims(token).get("role");
		return role == null ? "ADMIN" : role.toString();
	}

	public String getTokenType(String token) {
		Object tokenType = getClaims(token).get("tokenType");
		return tokenType == null ? "ACCESS" : tokenType.toString();
	}

	public boolean isAccessToken(String token) {
		return validateToken(token) && "ACCESS".equals(getTokenType(token));
	}

	public boolean validateToken(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	private Claims getClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	private byte[] resolveKeyBytes(String secret) {
		byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
		if (bytes.length >= 32) {
			return bytes;
		}
		byte[] padded = new byte[32];
		System.arraycopy(bytes, 0, padded, 0, bytes.length);
		return padded;
	}

	public long getRefreshExpirationMillis() {
		return refreshExpirationMillis;
	}
}
