package org.one.global.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final ApiUserContext principal;
	private final Object credentials;

	public JwtAuthenticationToken(ApiUserContext principal, Object credentials) {
		super(resolveAuthorities(principal));
		this.principal = principal;
		this.credentials = credentials;
		setAuthenticated(principal != null && principal.authenticated());
	}

	public JwtAuthenticationToken(Object principal, Object credentials) {
		super(Collections.emptyList());
		this.principal = ApiUserContext.empty();
		this.credentials = credentials;
		setAuthenticated(false);
	}

	private static Collection<? extends GrantedAuthority> resolveAuthorities(ApiUserContext principal) {
		if (principal == null || !principal.authenticated() || principal.role() == null || principal.role().isBlank()) {
			return List.of();
		}
		return List.of(new SimpleGrantedAuthority("ROLE_" + principal.role()));
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
}