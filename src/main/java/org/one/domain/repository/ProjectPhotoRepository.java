package org.one.domain.repository;

import java.util.List;
import java.util.Optional;
import org.one.domain.entity.ProjectPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 프로젝트 사진 조회와 삭제를 담당하는 JPA Repository입니다.
 */
public interface ProjectPhotoRepository extends JpaRepository<ProjectPhoto, Long> {

	/**
	 * 특정 프로젝트의 사진을 노출 순서 오름차순으로 조회합니다.
	 *
	 * @param projectId 프로젝트 ID
	 * @return 정렬된 프로젝트 사진 목록
	 */
	List<ProjectPhoto> findByProjectEvent_ProjectIdOrderByPriorityAsc(Long projectId);

	/**
	 * 특정 프로젝트에 연결된 모든 사진을 삭제합니다.
	 *
	 * @param projectId 프로젝트 ID
	 */
	void deleteByProjectEvent_ProjectId(Long projectId);

	/**
	 * 사진 ID와 프로젝트 ID로 사진을 조회합니다.
	 *
	 * @param photoId 사진 ID
	 * @param projectId 프로젝트 ID
	 * @return 사진 (없으면 empty)
	 */
	Optional<ProjectPhoto> findByPhotoIdAndProjectEvent_ProjectId(Long photoId, Long projectId);
}
