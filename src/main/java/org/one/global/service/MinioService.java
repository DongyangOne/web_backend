package org.one.global.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.one.global.config.minio.MinioConfig;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * MinIO 객체 스토리지와 연동하여 Presigned URL을 생성합니다.
 *
 * <p>파일 업로드는 클라이언트가 Presigned PUT URL로 직접 MinIO에 전송하고,
 * 파일 조회는 Presigned GET URL을 통해 접근합니다.
 */
@Service
@RequiredArgsConstructor
public class MinioService {

	private final MinioClient minioClient;
	private final MinioConfig minioConfig;

	/**
	 * 파일 업로드용 Presigned PUT URL을 생성합니다.
	 * 클라이언트는 이 URL로 HTTP PUT 요청을 보내 파일을 업로드합니다.
	 *
	 * @param objectKey 저장할 객체 키 (예: "projects/1/photo.jpg")
	 * @return Presigned PUT URL
	 */
	public String generateUploadUrl(String objectKey) {
		return getPresignedUrl(Method.PUT, objectKey);
	}

	/**
	 * 파일 조회용 Presigned GET URL을 생성합니다.
	 * 클라이언트는 이 URL로 파일을 다운로드하거나 표시합니다.
	 *
	 * @param objectKey 조회할 객체 키 (예: "projects/1/photo.jpg")
	 * @return Presigned GET URL
	 */
	public String generateDownloadUrl(String objectKey) {
		return getPresignedUrl(Method.GET, objectKey);
	}

	private String getPresignedUrl(Method method, String objectKey) {
		try {
			return minioClient.getPresignedObjectUrl(
					GetPresignedObjectUrlArgs.builder()
							.method(method)
							.bucket(minioConfig.getBucketName())
							.object(objectKey)
							.expiry(minioConfig.getPresignedExpiry(), TimeUnit.SECONDS)
							.build()
			);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}
}
