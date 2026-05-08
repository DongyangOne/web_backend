package org.one.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("ONE Backend API")
						.description("ONE 웹사이트 백엔드 API 문서")
						.version("1.0.0")
						.contact(new Contact().name("ONE Team").email("contact@one.dev"))
						.license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
	}
}