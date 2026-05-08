package org.one.global.config;

import org.one.global.file.config.FileUploadProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final FileUploadProperties fileUploadProperties;

	@Value("${app.cors.allowed-origins:http://localhost:3000}")
	private String[] allowedOrigins;

	public WebConfig(FileUploadProperties fileUploadProperties) {
		this.fileUploadProperties = fileUploadProperties;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
				.allowedOrigins(allowedOrigins)
				.allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
				.allowedHeaders("*")
				.allowCredentials(true)
				.maxAge(3600);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String uploadDir = fileUploadProperties.getUploadDir();
		registry.addResourceHandler("/files/**")
				.addResourceLocations("file:" + uploadDir + "/");
	}
}