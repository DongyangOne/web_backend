package org.one.main.repository;

import org.one.main.domain.ActivityCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주요활동 카드 조회를 담당하는 JPA Repository입니다.
 */
public interface ActivityCardRepository extends JpaRepository<ActivityCard, Long> {
}
