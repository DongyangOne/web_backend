package org.one.domain.applicant.repository;

import java.time.LocalDateTime;
import org.one.domain.applicant.entity.ApplicantMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApplicantMemberRepository extends JpaRepository<ApplicantMember, Long> {

	@Modifying
	@Query("DELETE FROM ApplicantMember a WHERE a.createdAt < :cutoff")
	void deleteByCreatedAtBefore(@Param("cutoff") LocalDateTime cutoff);
}