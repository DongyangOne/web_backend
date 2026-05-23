package org.one.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ProjectSaveRequestDto;
import org.one.domain.dto.request.ProjectUpdateRequestDto;
import org.one.domain.dto.response.ProjectDetailResponseDto;
import org.one.domain.entity.ProjectEvent;
import org.one.domain.entity.ProjectPhoto;
import org.one.domain.entity.ProjectTechStack;
import org.one.domain.repository.MainPageConfigRepository;
import org.one.domain.repository.ProjectEventRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.global.service.MinioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 프로젝트 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminProjectService {

	private static final Logger log = LoggerFactory.getLogger(AdminProjectService.class);
	private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");

	private final MainPageConfigRepository mainPageConfigRepository;
	private final ProjectEventRepository projectEventRepository;
	private final MinioService minioService;

	/**
	 * 프로젝트를 생성합니다.
	 * 사진은 선택 사항이며, 제공된 경우 MinIO에 업로드 후 URL을 저장합니다.
	 *
	 * @param request 생성 요청 DTO
	 * @param photos 사진 파일 목록 (선택, 최대 3개, 이미지만 허용)
	 * @return 생성된 프로젝트 상세 응답 DTO
	 */
	public ProjectDetailResponseDto save(ProjectSaveRequestDto request, List<MultipartFile> photos) {
		validateDateRange(request.getStartDate(), request.getEndDate());
		if (photos != null && !photos.isEmpty()) {
			validatePhotoCount(photos.size());
			validatePhotoTypes(photos);
		}

		ProjectEvent project = new ProjectEvent(
				mainPageConfigRepository.getConfig(),
				request.getYear(),
				request.getProjectName(),
				request.getAward(),
				request.getActivity(),
				request.getStartDate(),
				request.getEndDate(),
				request.getParticipantCount(),
				request.getDescription()
		);
		request.getTechStacks().forEach(name ->
				project.getTechStacks().add(new ProjectTechStack(project, name)));

		projectEventRepository.save(project);

		if (photos != null) {
			for (int i = 0; i < photos.size(); i++) {
				String objectKey = "projects/" + UUID.randomUUID();
				String url = minioService.uploadFile(photos.get(i), objectKey);
				project.getPhotos().add(new ProjectPhoto(project, url, i));
			}
		}

		return ProjectDetailResponseDto.from(project);
	}

	/**
	 * 프로젝트 정보를 수정합니다.
	 * keepPhotoIds에 포함되지 않은 기존 사진은 MinIO에서 삭제되고,
	 * newPhotos로 전달된 파일은 MinIO에 업로드되어 추가됩니다.
	 *
	 * @param projectId 수정할 프로젝트 ID
	 * @param request 수정 요청 DTO (keepPhotoIds: 유지할 기존 사진 ID 목록)
	 * @param newPhotos 새로 추가할 사진 파일 목록 (없으면 null)
	 * @return 수정된 프로젝트 상세 응답 DTO
	 */
	public ProjectDetailResponseDto update(Long projectId, ProjectUpdateRequestDto request, List<MultipartFile> newPhotos) {
		validateDateRange(request.getStartDate(), request.getEndDate());

		ProjectEvent project = projectEventRepository.findById(projectId)
				.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

		List<Long> keepPhotoIds = request.getKeepPhotoIds() != null ? request.getKeepPhotoIds() : List.of();
		int newCount = newPhotos != null ? newPhotos.size() : 0;

		validatePhotoCount(keepPhotoIds.size() + newCount);
		validatePhotosBelongToProject(keepPhotoIds, project);
		if (newPhotos != null) {
			validatePhotoTypes(newPhotos);
		}

		project.getPhotos().stream()
				.filter(photo -> !keepPhotoIds.contains(photo.getPhotoId()))
				.forEach(photo -> {
					try {
						minioService.deleteFile(minioService.extractObjectKey(photo.getPhotoUrl()));
					} catch (Exception e) {
						log.warn("[AdminProjectService] MinIO 사진 삭제 실패: {}", photo.getPhotoUrl(), e);
					}
				});
		project.getPhotos().removeIf(photo -> !keepPhotoIds.contains(photo.getPhotoId()));

		project.update(
				request.getYear(),
				request.getProjectName(),
				request.getAward(),
				request.getActivity(),
				request.getStartDate(),
				request.getEndDate(),
				request.getParticipantCount(),
				request.getDescription()
		);

		project.getTechStacks().clear();
		request.getTechStacks().forEach(name ->
				project.getTechStacks().add(new ProjectTechStack(project, name)));

		if (newPhotos != null) {
			int priorityStart = project.getPhotos().size();
			for (int i = 0; i < newPhotos.size(); i++) {
				String objectKey = "projects/" + UUID.randomUUID();
				String url = minioService.uploadFile(newPhotos.get(i), objectKey);
				project.getPhotos().add(new ProjectPhoto(project, url, priorityStart + i));
			}
		}

		return ProjectDetailResponseDto.from(project);
	}

	/**
	 * 사진 총 개수가 최대 3개를 초과하지 않는지 검증합니다.
	 *
	 * @param totalCount 유지 사진 수 + 신규 사진 수
	 */
	private void validatePhotoCount(int totalCount) {
		if (totalCount > 3) {
			throw new BusinessException(ErrorCode.PHOTO_LIMIT_EXCEEDED);
		}
	}

	/**
	 * keepPhotoIds의 사진이 모두 해당 프로젝트 소속인지 검증합니다.
	 *
	 * @param keepPhotoIds 유지할 사진 ID 목록
	 * @param project 프로젝트 엔티티
	 */
	private void validatePhotosBelongToProject(List<Long> keepPhotoIds, ProjectEvent project) {
		Set<Long> existingIds = project.getPhotos().stream()
				.map(ProjectPhoto::getPhotoId)
				.collect(Collectors.toSet());
		if (!existingIds.containsAll(keepPhotoIds)) {
			throw new BusinessException(ErrorCode.INVALID_INPUT, "유지할 사진 ID가 해당 프로젝트에 속하지 않습니다.");
		}
	}

	/**
	 * 사진 파일의 콘텐츠 타입이 이미지인지, 확장자가 허용된 형식인지 검증합니다.
	 *
	 * @param photos 검증할 사진 파일 목록
	 */
	private void validatePhotoTypes(List<MultipartFile> photos) {
		for (MultipartFile photo : photos) {
			String contentType = photo.getContentType();
			if (contentType == null || !contentType.startsWith("image/")) {
				throw new BusinessException(ErrorCode.INVALID_INPUT, "이미지 파일만 업로드할 수 있습니다.");
			}
			String originalFilename = photo.getOriginalFilename();
			if (originalFilename != null) {
				int dotIndex = originalFilename.lastIndexOf('.');
				String ext = dotIndex >= 0
						? originalFilename.substring(dotIndex + 1).toLowerCase()
						: "";
				if (!ALLOWED_EXTENSIONS.contains(ext)) {
					throw new BusinessException(ErrorCode.INVALID_INPUT,
							"허용되지 않는 확장자입니다. (허용: jpg, jpeg, png, gif, webp)");
				}
			}
		}
	}

	/**
	 * 시작일이 종료일보다 늦지 않은지 검증합니다.
	 *
	 * @param start 시작일
	 * @param end 종료일
	 */
	private void validateDateRange(LocalDate start, LocalDate end) {
		if (start != null && end != null && start.isAfter(end)) {
			throw new BusinessException(ErrorCode.INVALID_DATE_RANGE, "시작일은 종료일보다 늦을 수 없습니다.");
		}
	}
}
