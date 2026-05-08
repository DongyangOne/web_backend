package org.one.global.security;

public record ApiUserContext(
		Long userId,
		String role,
		boolean authenticated
) {
	public boolean isAdmin() {
		return "ADMIN".equals(role);
	}

	public static ApiUserContext anonymous(Long userId) {
		return new ApiUserContext(userId, "ANONYMOUS", false);
	}

	public static ApiUserContext authenticated(Long userId, String role) {
		return new ApiUserContext(userId, role, true);
	}

	public static ApiUserContext empty() {
		return new ApiUserContext(null, "ANONYMOUS", false);
	}
}