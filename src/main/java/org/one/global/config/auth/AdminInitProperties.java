package org.one.global.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml의 admin.init.* 설정 값을 바인딩합니다.
 */
@Component
@ConfigurationProperties(prefix = "admin.init")
public class AdminInitProperties {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
