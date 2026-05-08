package org.one.global.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class)
				&& ApiUserContext.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) {
		CurrentUser annotation = parameter.getParameterAnnotation(CurrentUser.class);
		boolean required = annotation != null && annotation.required();

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof ApiUserContext apiUserContext) {
			return apiUserContext;
		}

		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request != null) {
			String userIdHeader = request.getHeader("X-USER-ID");
			if (userIdHeader != null && !userIdHeader.isBlank()) {
				try {
					return ApiUserContext.anonymous(Long.valueOf(userIdHeader));
				} catch (NumberFormatException exception) {
					if (required) {
						throw new IllegalArgumentException("X-USER-ID 헤더는 숫자여야 합니다.");
					}
				}
			}
		}

		if (required) {
			throw new IllegalStateException("인증된 사용자 정보가 필요합니다.");
		}

		return ApiUserContext.empty();
	}
}