package org.one.domain.repository;

import org.one.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 정규 부원 데이터 조회와 일괄 갱신을 담당하는 JPA Repository입니다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

	/**
	 * 특정 학번의 부원이 이미 존재하는지 확인합니다.
	 *
	 * @param studentId 학번
	 * @return 존재하면 true
	 */
	boolean existsByStudentId(String studentId);

	/**
	 * 모든 정규 부원의 학년과 나이를 1씩 증가시킵니다.
	 */
	@Modifying
	@Query("UPDATE Member m SET m.grade = m.grade + 1, m.age = m.age + 1")
	void incrementGradeAndAge();
}
