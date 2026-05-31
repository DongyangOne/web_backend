package org.one.main.repository;

import org.one.main.domain.MainPageConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 메인 페이지 싱글톤 설정 조회를 담당하는 JPA Repository입니다.
 */
public interface MainPageConfigRepository extends JpaRepository<MainPageConfig, Integer> {

	/**
	 * main_id가 1인 설정을 조회하고, 없으면 기본 설정을 생성합니다.
	 *
	 * @return 메인 페이지 설정
	 */
	default MainPageConfig getConfig() {
		return findById(1).orElseGet(() -> save(MainPageConfig.singleton()));
	}
}
