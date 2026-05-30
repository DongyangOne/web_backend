package org.one.project.repository;

import org.one.project.domain.ProjectTechStack;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 프로젝트 기술 스택 조회를 담당하는 JPA Repository입니다.
 */
public interface ProjectTechStackRepository extends JpaRepository<ProjectTechStack, Long> {
}
