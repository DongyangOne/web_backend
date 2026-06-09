package org.one.applicant.repository;

import java.time.LocalDateTime;
import org.one.applicant.domain.ApplicantMember;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 가입 신청자 데이터 조회와 정리 작업을 담당하는 JPA Repository입니다.
 */
public interface ApplicantMemberRepository extends JpaRepository<ApplicantMember, Long> {

	/**
	 * 기준 시각보다 오래된 가입 신청 데이터를 삭제합니다.
	 *
	 * @param cutoff 삭제 기준 시각
	 */
	@Modifying
	@Query("DELETE FROM ApplicantMember a WHERE a.createdAt < :cutoff")
	void deleteByCreatedAtBefore(@Param("cutoff") LocalDateTime cutoff);

	/**
	 * 모든 신청 부원의 데이터를 리스트로 가져옴.
	 *
	 * @param pageable 페이지 기본 설정
	 */
	List<ApplicantMember> findAllBy(Pageable pageable);
}
