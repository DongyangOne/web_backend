package org.one.domain.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ProjectUpdateRequestDto;
import org.one.domain.dto.response.ProjectDetailResponseDto;
import org.one.domain.entity.ProjectEvent;
import org.one.domain.entity.ProjectPhoto;
import org.one.domain.entity.ProjectTechStack;
import org.one.domain.repository.ProjectEventRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.global.service.MinioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 프로젝트 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminProjectService {

	private static final Logger log = LoggerFactory.getLogger(AdminProjectService.class);
	private static final String PHOTO_KEY_PREFIX = "projects/";

	private final ProjectEventRepository projectEventRepository;
	private final MinioService minioService;

	/**
	 * 프로젝트 정보를 수정합니다.
	 * keepPhotoIds에 포함되지 않은 기존 사진은 MinIO에서 삭제됩니다.
	 * 새 사진은 클라이언트가 Presigned URL로 MinIO에 직접 업로드한 뒤
	 * objectKey 목록을 DTO의 newPhotoKeys에 담아 전달합니다.
	 *
	 * @param projectId 수정할 프로젝트 ID
	 * @param request 수정 요청 DTO (keepPhotoIds: 유지할 기존 사진 ID / newPhotoKeys: 새 사진 objectKey)
	 * @return 수정된 프로젝트 상세 응답 DTO
	 */
	public ProjectDetailResponseDto update(Long projectId, ProjectUpdateRequestDto request) {
		validateDateRange(request.getStartDate(), request.getEndDate());

		ProjectEvent project = projectEventRepository.findById(projectId)
				.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));

		List<Long> keepPhotoIds = request.getKeepPhotoIds() != null ? request.getKeepPhotoIds() : List.of();
		List<String> newPhotoKeys = request.getNewPhotoKeys() != null ? request.getNewPhotoKeys() : List.of();

		validatePhotoCount(keepPhotoIds.size() + newPhotoKeys.size());
		validatePhotosBelongToProject(keepPhotoIds, project);
		validatePhotoKeys(newPhotoKeys);

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

		int priorityStart = project.getPhotos().size();
		for (int i = 0; i < newPhotoKeys.size(); i++) {
			String url = minioService.getObjectUrl(newPhotoKeys.get(i));
			project.getPhotos().add(new ProjectPhoto(project, url, priorityStart + i));
		}

		return ProjectDetailResponseDto.from(project);
	}

	/**
	 * photoKey 목록이 올바른 경로 prefix를 가지며 중복이 없는지 검증합니다.
	 *
	 * @param photoKeys 검증할 objectKey 목록
	 */
	private void validatePhotoKeys(List<String> photoKeys) {
		Set<String> seen = new HashSet<>();
		for (String key : photoKeys) {
			if (!key.startsWith(PHOTO_KEY_PREFIX)) {
				throw new BusinessException(ErrorCode.INVALID_OBJECT_KEY);
			}
			if (!seen.add(key)) {
				throw new BusinessException(ErrorCode.DUPLICATE_RESOURCE);
			}
		}
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
			throw new BusinessException(ErrorCode.INVALID_INPUT);
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
			throw new BusinessException(ErrorCode.INVALID_DATE_RANGE);
		}
	}
}
