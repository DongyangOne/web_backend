package org.one.domain.project.repository;

import java.util.List;
import org.one.domain.project.entity.ProjectEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectEventRepository extends JpaRepository<ProjectEvent, Long> {

	List<ProjectEvent> findAllByOrderByPriorityAsc();
}
