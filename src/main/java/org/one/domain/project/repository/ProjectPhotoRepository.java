package org.one.domain.project.repository;

import java.util.List;
import org.one.domain.project.entity.ProjectPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectPhotoRepository extends JpaRepository<ProjectPhoto, Long> {

	List<ProjectPhoto> findByProjectEvent_ProjectIdOrderByPriorityAsc(Long projectId);

	void deleteByProjectEvent_ProjectId(Long projectId);
}
