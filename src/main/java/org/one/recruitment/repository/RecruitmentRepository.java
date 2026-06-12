package org.one.recruitment.repository;

import org.one.recruitment.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 모집 공고 조회/저장을 담당하는 JPA Repository입니다.
 */
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {
}
