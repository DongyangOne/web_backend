package org.one.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	private static final String BEARER_SCHEME = "bearerAuth";

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("ONE Backend API")
						.description("ONE 웹사이트 백엔드 API 문서")
						.version("1.0.0")
						.contact(new Contact().name("ONE Team").email("contact@one.dev"))
						.license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
					.components(new Components()
							.addSecuritySchemes(BEARER_SCHEME, new SecurityScheme()
									.type(SecurityScheme.Type.HTTP)
									.scheme("bearer")
									.bearerFormat("JWT")))
						.addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME));
	}

	@Bean
	public GroupedOpenApi authApi() {
		return GroupedOpenApi.builder()
				.group("auth")
				.pathsToMatch("/auth/**")
				.build();
	}

	@Bean
	public GroupedOpenApi fileApi() {
		return GroupedOpenApi.builder()
				.group("files")
				.pathsToMatch("/api/files/**")
				.build();
	}
}