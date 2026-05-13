package org.one.global.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml의 jwt.* 설정 값을 바인딩합니다.
 */
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

	private String secret;
	private long accessExpiration;
	private long refreshExpiration;

	/**
	 * JWT 서명에 사용할 secret 값을 반환합니다.
	 *
	 * @return JWT secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * JWT secret 값을 설정합니다.
	 *
	 * @param secret JWT secret
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * Access Token 만료 시간을 밀리초 단위로 반환합니다.
	 *
	 * @return Access Token 만료 밀리초
	 */
	public long getAccessExpiration() {
		return accessExpiration;
	}

	/**
	 * Access Token 만료 시간을 설정합니다.
	 *
	 * @param accessExpiration Access Token 만료 밀리초
	 */
	public void setAccessExpiration(long accessExpiration) {
		this.accessExpiration = accessExpiration;
	}

	/**
	 * Refresh Token 만료 시간을 밀리초 단위로 반환합니다.
	 *
	 * @return Refresh Token 만료 밀리초
	 */
	public long getRefreshExpiration() {
		return refreshExpiration;
	}

	/**
	 * Refresh Token 만료 시간을 설정합니다.
	 *
	 * @param refreshExpiration Refresh Token 만료 밀리초
	 */
	public void setRefreshExpiration(long refreshExpiration) {
		this.refreshExpiration = refreshExpiration;
	}
}
