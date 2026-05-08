package org.one.domain.mainpage.repository;

import org.one.domain.mainpage.entity.MainPageConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainPageConfigRepository extends JpaRepository<MainPageConfig, Integer> {

	default MainPageConfig getConfig() {
		return findById(1).orElseGet(() -> save(MainPageConfig.singleton()));
	}
}
