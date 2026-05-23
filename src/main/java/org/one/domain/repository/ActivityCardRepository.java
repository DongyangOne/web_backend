package org.one.domain.repository;

import org.one.domain.entity.ActivityCard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 주요활동 카드 조회를 담당하는 JPA Repository입니다.
 */
public interface ActivityCardRepository extends JpaRepository<ActivityCard, Long> {
}
