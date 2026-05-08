package org.one.domain.admin.repository;

import java.util.Optional;
import org.one.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Optional<Admin> findByUsername(String username);
}
