package org.one.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.one.global.security.JwtAuthenticationFilter;
import org.one.global.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Spring Security 인증/인가, JWT 필터, CORS 정책을 설정합니다.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private static final String[] PUBLIC_URLS = {
			"/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**",
			"/actuator/health",
			"/api/v1/auth/**"
	};

	@Value("${app.cors.allowed-origins}")
	private String[] allowedOrigins;

	private final JwtTokenProvider jwtTokenProvider;

	/**
	 * 보안 설정에 필요한 JWT 처리 컴포넌트를 주입받습니다.
	 *
	 * @param jwtTokenProvider JWT 인증 처리 컴포넌트
	 */
	public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	/**
	 * JSON 직렬화/역직렬화 도구를 등록합니다.
	 *
	 * @return ObjectMapper
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	/**
	 * 관리자 비밀번호 검증에 사용할 BCrypt PasswordEncoder를 등록합니다.
	 *
	 * @return BCrypt PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Stateless JWT 기반 보안 필터 체인을 구성합니다.
	 *
	 * @param http Spring Security HttpSecurity
	 * @param objectMapper JSON 직렬화 도구
	 * @return SecurityFilterChain
	 * @throws Exception 보안 설정 구성 예외
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(PUBLIC_URLS).permitAll()
						.anyRequest().hasRole("ADMIN")
				)
				.exceptionHandling(exc -> exc
						.authenticationEntryPoint((request, response, authException) ->
								writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED,
										ErrorCode.UNAUTHORIZED, objectMapper))
						.accessDeniedHandler((request, response, accessDeniedException) ->
								writeErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
										ErrorCode.FORBIDDEN, objectMapper))
				)
				.addFilterBefore(
						new JwtAuthenticationFilter(jwtTokenProvider, objectMapper),
						UsernamePasswordAuthenticationFilter.class
				);

		return http.build();
	}

	/**
	 * CORS 정책을 Security 레이어에서 함께 관리합니다.
	 *
	 * @return CORS 설정 소스
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList(allowedOrigins));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	/**
	 * 인증/인가 실패를 공통 ApiResponse JSON으로 작성합니다.
	 *
	 * @param response HTTP 응답
	 * @param status HTTP 상태 코드
	 * @param errorCode 공통 에러 코드
	 * @param objectMapper JSON 직렬화 도구
	 * @throws IOException 응답 본문 작성 예외
	 */
	private void writeErrorResponse(HttpServletResponse response, int status, ErrorCode errorCode,
			ObjectMapper objectMapper)
			throws IOException {
		response.setStatus(status);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(errorCode)));
	}
}
