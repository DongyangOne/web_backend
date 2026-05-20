package org.one.domain.service;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.one.domain.dto.request.PhotoOrderItem;
import org.one.domain.dto.request.PhotoRequest;
import org.one.domain.dto.request.PhotoReorderRequest;
import org.one.domain.dto.request.ProjectCreateRequest;
import org.one.domain.dto.request.ProjectUpdateRequest;
import org.one.domain.dto.response.PhotoResponse;
import org.one.domain.dto.response.ProjectResponse;
import org.one.domain.entity.MainPageConfig;
import org.one.domain.entity.ProjectEvent;
import org.one.domain.entity.ProjectPhoto;
import org.one.domain.repository.MainPageConfigRepository;
import org.one.domain.repository.ProjectEventRepository;
import org.one.domain.repository.ProjectPhotoRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리자 프로젝트 생성/수정 및 사진 관리를 담당합니다.
 */
@Service
@Transactional
public class AdminProjectService {

	private final ProjectEventRepository projectEventRepository;
	private final ProjectPhotoRepository projectPhotoRepository;
	private final MainPageConfigRepository mainPageConfigRepository;
	private final EntityManager entityManager;

	/**
	 * 프로젝트 서비스에 필요한 저장소를 주입받습니다.
	 *
	 * @param projectEventRepository 프로젝트 저장소
	 * @param projectPhotoRepository 프로젝트 사진 저장소
	 * @param mainPageConfigRepository 메인 페이지 설정 저장소
	 * @param entityManager JPA EntityManager
	 */
	public AdminProjectService(ProjectEventRepository projectEventRepository,
			ProjectPhotoRepository projectPhotoRepository,
			MainPageConfigRepository mainPageConfigRepository,
			EntityManager entityManager) {
		this.projectEventRepository = projectEventRepository;
		this.projectPhotoRepository = projectPhotoRepository;
		this.mainPageConfigRepository = mainPageConfigRepository;
		this.entityManager = entityManager;
	}

	/**
	 * 프로젝트를 생성합니다.
	 *
	 * @param request 프로젝트 생성 요청
	 * @return 생성된 프로젝트 응답
	 */
	public ProjectResponse create(ProjectCreateRequest request) {
		MainPageConfig config = mainPageConfigRepository.getConfig();
		ProjectEvent event = new ProjectEvent(
				config,
				request.projectName(),
				request.participantCount(),
				request.description(),
				request.priority()
		);
		projectEventRepository.save(event);
		savePhotos(event, request.photos());
		return ProjectResponse.from(reload(event.getProjectId()));
	}

	/**
	 * 프로젝트에 사진을 추가합니다.
	 *
	 * @param projectId 프로젝트 ID
	 * @param request 사진 추가 요청
	 * @return 추가된 사진 응답
	 */
	public PhotoResponse addPhoto(Long projectId, PhotoRequest request) {
		ProjectEvent event = findProjectById(projectId);
		ProjectPhoto photo = new ProjectPhoto(event, request.photoUrl(), request.priority());
		projectPhotoRepository.save(photo);
		return PhotoResponse.from(photo);
	}

	/**
	 * 프로젝트 기본 정보를 수정합니다. 사진은 별도 엔드포인트로 관리합니다.
	 *
	 * @param projectId 프로젝트 ID
	 * @param request 프로젝트 수정 요청
	 * @return 수정된 프로젝트 응답
	 */
	public ProjectResponse update(Long projectId, ProjectUpdateRequest request) {
		ProjectEvent event = findProjectById(projectId);
		event.update(
				request.projectName(),
				request.participantCount(),
				request.description(),
				request.priority()
		);
		return ProjectResponse.from(reload(projectId));
	}

	/**
	 * 사진을 삭제합니다.
	 *
	 * @param projectId 프로젝트 ID
	 * @param photoId 사진 ID
	 */
	public void deletePhoto(Long projectId, Long photoId) {
		ProjectPhoto photo = projectPhotoRepository
				.findByPhotoIdAndProjectEvent_ProjectId(photoId, projectId)
				.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
		projectPhotoRepository.delete(photo);
	}

	/**
	 * 프로젝트 사진 순서를 일괄 변경합니다.
	 * 요청에 포함되지 않은 사진의 순서는 변경되지 않습니다.
	 *
	 * @param projectId 프로젝트 ID
	 * @param request 순서 변경 요청
	 * @return 변경 후 프로젝트 응답
	 */
	public ProjectResponse reorderPhotos(Long projectId, PhotoReorderRequest request) {
		findProjectById(projectId);

		for (PhotoOrderItem item : request.orders()) {
			ProjectPhoto photo = projectPhotoRepository
					.findByPhotoIdAndProjectEvent_ProjectId(item.photoId(), projectId)
					.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
			photo.updatePriority(item.priority());
		}

		return ProjectResponse.from(reload(projectId));
	}

	private ProjectEvent findProjectById(Long projectId) {
		return projectEventRepository.findById(projectId)
				.orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
	}

	private void savePhotos(ProjectEvent event, List<PhotoRequest> photos) {
		if (photos == null || photos.isEmpty()) {
			return;
		}
		List<ProjectPhoto> photoEntities = photos.stream()
				.map(p -> new ProjectPhoto(event, p.photoUrl(), p.priority()))
				.toList();
		projectPhotoRepository.saveAll(photoEntities);
	}

	private ProjectEvent reload(Long projectId) {
		projectEventRepository.flush();
		projectPhotoRepository.flush();
		ProjectEvent event = findProjectById(projectId);
		entityManager.refresh(event);
		return event;
	}
}
