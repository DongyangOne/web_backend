package org.one.main.repository;

import org.one.main.domain.MainPageConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 메인 페이지 싱글톤 설정 조회를 담당하는 JPA Repository입니다.
 */
public interface MainPageConfigRepository extends JpaRepository<MainPageConfig, Integer> {
}
