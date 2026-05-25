package org.one.domain.repository;

import org.one.domain.entity.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 모집 공고 조회/저장을 담당하는 JPA Repository입니다.
 */
public interface RecruitmentRepository extends JpaRepository<Recruitment, Integer> {

	/**
	 * 싱글톤 모집 공고 엔티티(ID=1)를 조회하고, 없으면 기본 값으로 생성합니다.
	 *
	 * @return 모집 공고 엔티티
	 */
	default Recruitment findRecruitment() {
		return findById(1).orElseGet(() -> save(Recruitment.singleton()));
	}
}
