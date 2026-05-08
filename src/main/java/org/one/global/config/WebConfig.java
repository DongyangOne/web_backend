package org.one.global.config;

import org.one.global.config.props.CorsProperties;
import org.one.global.file.config.FileUploadProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final FileUploadProperties fileUploadProperties;
	private final CorsProperties corsProperties;

	public WebConfig(FileUploadProperties fileUploadProperties, CorsProperties corsProperties) {
		this.fileUploadProperties = fileUploadProperties;
		this.corsProperties = corsProperties;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/**")
				.allowedOrigins(corsProperties.getAllowedOrigins())
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