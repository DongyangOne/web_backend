package org.one.global.config.minio;

import io.minio.MinioClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MinIO 객체 스토리지 클라이언트 설정입니다.
 * application.yml의 minio.* 프로퍼티를 바인딩합니다.
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class MinioConfig {

	/** MinIO 서버 주소 (예: http://localhost:9000) */
	private String url;

	/** 외부 접근용 MinIO 공개 URL (Presigned URL, 파일 접근 URL 생성에 사용) */
	private String publicUrl;

	/** MinIO Access Key */
	private String accessKey;

	/** MinIO Secret Key */
	private String secretKey;

	/** 파일을 저장할 버킷 이름 */
	private String bucketName;

	/** Presigned URL 유효 시간 (초, 기본 3600 = 1시간) */
	private int presignedExpiry;

	/**
	 * MinIO 클라이언트 빈을 생성합니다.
	 *
	 * @return 설정된 MinioClient
	 */
	@Bean
	public MinioClient minioClient() {
		return MinioClient.builder()
				.endpoint(url)
				.credentials(accessKey, secretKey)
				.build();
	}
}
