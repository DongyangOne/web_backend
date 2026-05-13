package org.one.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 요청 Authorization 헤더의 Bearer 토큰을 검증해 SecurityContext에 인증 정보를 저장합니다.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final ObjectMapper objectMapper;

	/**
	 * JWT 검증기와 JSON 직렬화 도구를 주입받습니다.
	 *
	 * @param jwtTokenProvider JWT 처리 컴포넌트
	 * @param objectMapper 공통 응답 JSON 작성용 ObjectMapper
	 */
	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, ObjectMapper objectMapper) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.objectMapper = objectMapper;
	}

	/**
	 * Bearer 토큰이 있으면 Authentication 생성을 JwtTokenProvider에 위임합니다.
	 *
	 * @param request HTTP 요청
	 * @param response HTTP 응답
	 * @param filterChain 다음 필터 체인
	 * @throws ServletException 필터 처리 예외
	 * @throws IOException 응답 작성 예외
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeader != null) {
			if (!authorizationHeader.startsWith("Bearer ")) {
				sendUnauthorized(response);
				return;
			}

			try {
				String token = authorizationHeader.substring(7);
				Authentication authentication = jwtTokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (RuntimeException exception) {
				SecurityContextHolder.clearContext();
				sendUnauthorized(response);
				return;
			}
		}

		filterChain.doFilter(request, response);
	}

	/**
	 * 유효하지 않은 토큰에 대해 공통 실패 응답 형식의 401 응답을 작성합니다.
	 *
	 * @param response HTTP 응답
	 * @throws IOException 응답 본문 작성 예외
	 */
	private void sendUnauthorized(HttpServletResponse response) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(ErrorCode.INVALID_TOKEN)));
	}
}
