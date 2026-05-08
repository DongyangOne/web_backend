package org.one.domain.member.repository;

import org.one.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByStudentId(String studentId);

	@Modifying
	@Query("UPDATE Member m SET m.grade = m.grade + 1, m.age = m.age + 1")
	void incrementGradeAndAge();
}
