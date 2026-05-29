package org.one.project.repository;

import java.util.List;
import org.one.project.domain.ProjectEvent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 프로젝트 행사 조회를 담당하는 JPA Repository입니다.
 */
public interface ProjectEventRepository extends JpaRepository<ProjectEvent, Long> {

	/**
	 * 모든 프로젝트 행사를 년도 오름차순으로 조회합니다.
	 *
	 * @return 정렬된 프로젝트 행사 목록
	 */
	List<ProjectEvent> findAllByOrderByYearAsc();
}
