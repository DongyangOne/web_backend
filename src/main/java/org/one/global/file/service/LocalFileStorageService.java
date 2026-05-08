package org.one.global.file.service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;
import org.one.global.apiPayload.code.ErrorCode;
import org.one.global.apiPayload.exception.BusinessException;
import org.one.global.file.config.FileUploadProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalFileStorageService implements FileStorageService {

	private static final Set<String> ALLOWED_TYPES = Set.of("image/jpeg", "image/png", "image/webp", "image/gif");
	private static final long MAX_SIZE = 10 * 1024 * 1024; // 10MB

	private final Path uploadPath;

	public LocalFileStorageService(FileUploadProperties properties) {
		this.uploadPath = Paths.get(properties.getUploadDir()).toAbsolutePath().normalize();
	}

	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(uploadPath);
		} catch (IOException e) {
			throw new IllegalStateException("업로드 디렉토리를 생성할 수 없습니다: " + uploadPath, e);
		}
	}

	@Override
	public String store(MultipartFile file) {
		validateFile(file);

		String extension = getExtension(file.getOriginalFilename());
		String storedFilename = UUID.randomUUID() + "." + extension;
		Path targetPath = uploadPath.resolve(storedFilename);

		try {
			Files.copy(file.getInputStream(), targetPath);
		} catch (IOException e) {
			throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
		}

		return "/files/" + storedFilename;
	}

	@Override
	public void delete(String storedPath) {
		if (storedPath == null || !storedPath.startsWith("/files/")) return;

		String filename = storedPath.substring("/files/".length());
		Path filePath = uploadPath.resolve(filename).normalize();

		if (!filePath.startsWith(uploadPath)) return; // 경로 탈출 방지

		try {
			Files.deleteIfExists(filePath);
		} catch (IOException ignored) {}
	}

	private void validateFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new BusinessException(ErrorCode.INVALID_INPUT, "파일이 비어있습니다.");
		}
		if (file.getSize() > MAX_SIZE) {
			throw new BusinessException(ErrorCode.INVALID_INPUT, "파일 크기는 10MB를 초과할 수 없습니다.");
		}
		if (!ALLOWED_TYPES.contains(file.getContentType())) {
			throw new BusinessException(ErrorCode.INVALID_INPUT, "이미지 파일만 업로드 가능합니다. (jpg, png, webp, gif)");
		}
	}

	private String getExtension(String originalFilename) {
		String cleaned = StringUtils.cleanPath(originalFilename != null ? originalFilename : "file");
		int dotIndex = cleaned.lastIndexOf('.');
		return (dotIndex >= 0) ? cleaned.substring(dotIndex + 1).toLowerCase() : "bin";
	}
}
