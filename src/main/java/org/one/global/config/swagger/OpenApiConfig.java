package org.one.global.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 문서의 기본 정보, 서버 URL, JWT 인증 스키마, 공통 에러 응답을 설정합니다.
 */
@Configuration
public class OpenApiConfig {

	private static final String BEARER_SCHEME = "bearerAuth";

	@Value("${springdoc.server.dev-url:http://localhost:8080}")
	private String devServerUrl;

	@Value("${springdoc.server.prod-url:https://api.example.com}")
	private String prodServerUrl;

	/**
	 * 전체 OpenAPI 문서에 공통 서버, JWT 인증, 에러 응답 컴포넌트를 등록합니다.
	 *
	 * @return OpenAPI 문서 설정
	 */
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.addServersItem(new Server().url(devServerUrl).description("개발 서버"))
				.addServersItem(new Server().url(prodServerUrl).description("운영 서버"))
				.info(new Info()
						.title("ONE Backend API")
						.description("ONE 웹사이트 백엔드 API 문서")
						.version("1.0.0")
						.contact(new Contact().name("ONE Team").email("contact@one.dev")))
				.components(new Components()
						.addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								.description("로그인 후 발급된 Access Token을 입력하세요. (Bearer 접두사 없이 토큰만 입력)"))
						.addResponses("BadRequest", badRequestResponse())
						.addResponses("Unauthorized", unauthorizedResponse())
						.addResponses("Forbidden", forbiddenResponse())
						.addResponses("InternalServerError", internalServerErrorResponse()))
				.addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME));
	}

	/**
	 * 인증 API만 모아 보여주는 Swagger 그룹을 생성합니다.
	 *
	 * @return 인증 API 그룹
	 */
	@Bean
	public GroupedOpenApi authApi() {
		return GroupedOpenApi.builder()
				.group("인증")
				.pathsToMatch("/auth/**")
				.build();
	}

	/**
	 * 관리자용 API 경로를 모아 보여주는 Swagger 그룹을 생성합니다.
	 *
	 * @return 관리자 API 그룹
	 */
	@Bean
	public GroupedOpenApi adminApi() {
		return GroupedOpenApi.builder()
				.group("관리자")
				.pathsToMatch("/api/**")
				.build();
	}

	/**
	 * 요청 검증 실패 공통 응답 예시를 생성합니다.
	 *
	 * @return 400 공통 응답
	 */
	private ApiResponse badRequestResponse() {
		return new ApiResponse()
				.description("잘못된 요청")
				.content(new Content().addMediaType("application/json",
						new MediaType().addExamples("INVALID_INPUT", new Example().value(
								errorExample("INVALID_INPUT", "입력값이 올바르지 않습니다.")))));
	}

	/**
	 * 인증 실패 공통 응답 예시를 생성합니다.
	 *
	 * @return 401 공통 응답
	 */
	private ApiResponse unauthorizedResponse() {
		return new ApiResponse()
				.description("인증 실패")
				.content(new Content().addMediaType("application/json",
						new MediaType()
								.addExamples("INVALID_TOKEN", new Example().value(
										errorExample("INVALID_TOKEN", "유효하지 않은 토큰입니다.")))
								.addExamples("INVALID_CREDENTIALS", new Example().value(
										errorExample("INVALID_CREDENTIALS",
												"아이디 또는 비밀번호가 올바르지 않습니다.")))));
	}

	/**
	 * 권한 부족 공통 응답 예시를 생성합니다.
	 *
	 * @return 403 공통 응답
	 */
	private ApiResponse forbiddenResponse() {
		return new ApiResponse()
				.description("권한 없음")
				.content(new Content().addMediaType("application/json",
						new MediaType().addExamples("FORBIDDEN", new Example().value(
								errorExample("FORBIDDEN", "접근 권한이 없습니다.")))));
	}

	/**
	 * 서버 오류 공통 응답 예시를 생성합니다.
	 *
	 * @return 500 공통 응답
	 */
	private ApiResponse internalServerErrorResponse() {
		return new ApiResponse()
				.description("서버 오류")
				.content(new Content().addMediaType("application/json",
						new MediaType().addExamples("INTERNAL_SERVER_ERROR", new Example().value(
								errorExample("INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.")))));
	}

	/**
	 * 공통 에러 응답 예시 JSON 문자열을 생성합니다.
	 *
	 * @param code 에러 코드
	 * @param message 에러 메시지
	 * @return Swagger 예시 JSON
	 */
	private String errorExample(String code, String message) {
		return """
				{"success":false,"code":"%s","message":"%s"}"""
				.formatted(code, message);
	}
}
