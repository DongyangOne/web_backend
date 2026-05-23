package org.one.global.service;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.one.global.config.minio.MinioConfig;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	/**
	 * 파일을 MinIO에 업로드하고 접근 URL을 반환합니다.
	 *
	 * @param file 업로드할 파일
	 * @param objectKey 저장할 객체 키 (예: "projects/uuid")
	 * @return 파일 접근 URL
	 */
	public String uploadFile(MultipartFile file, String objectKey) {
		try {
			minioClient.putObject(
					PutObjectArgs.builder()
							.bucket(minioConfig.getBucketName())
							.object(objectKey)
							.stream(file.getInputStream(), file.getSize(), -1)
							.contentType(file.getContentType())
							.build()
			);
			return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + objectKey;
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * MinIO에서 파일을 삭제합니다.
	 *
	 * @param objectKey 삭제할 객체 키
	 */
	public void deleteFile(String objectKey) {
		try {
			minioClient.removeObject(
					RemoveObjectArgs.builder()
							.bucket(minioConfig.getBucketName())
							.object(objectKey)
							.build()
			);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 객체 키로 MinIO 정적 접근 URL을 반환합니다.
	 * 클라이언트가 Presigned PUT URL로 업로드 완료 후, 저장할 URL을 얻을 때 사용합니다.
	 *
	 * @param objectKey 객체 키 (예: "logo/uuid", "projects/uuid")
	 * @return 정적 접근 URL
	 */
	public String getObjectUrl(String objectKey) {
		return minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/" + objectKey;
	}

	/**
	 * MinIO 파일 URL에서 객체 키를 추출합니다.
	 *
	 * @param url MinIO 파일 URL
	 * @return 객체 키
	 */
	public String extractObjectKey(String url) {
		String prefix = minioConfig.getUrl() + "/" + minioConfig.getBucketName() + "/";
		return url.substring(prefix.length());
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
