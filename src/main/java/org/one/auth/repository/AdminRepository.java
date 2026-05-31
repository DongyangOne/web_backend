package org.one.auth.repository;

import java.util.Optional;
import org.one.auth.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 관리자 계정 조회를 담당하는 JPA Repository입니다.
 */
public interface AdminRepository extends JpaRepository<Admin, Long> {

	/**
	 * 관리자 아이디로 계정을 조회합니다.
	 *
	 * @param username 관리자 아이디
	 * @return 조회된 관리자 계정
	 */
	Optional<Admin> findByUsername(String username);
}
