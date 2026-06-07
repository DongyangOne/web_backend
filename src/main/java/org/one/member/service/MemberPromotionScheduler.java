package org.one.member.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import org.one.member.domain.Member;
import org.one.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 매년 정규 부원의 학년 승급과 5학년 이상 부원 정리를 수행하는 스케줄러입니다.
 */
@Component
public class MemberPromotionScheduler {

    private static final Logger log = LoggerFactory.getLogger(MemberPromotionScheduler.class);

    private final MemberRepository memberRepository;

    /**
     * 부원 저장소를 주입받습니다.
     *
     * @param memberRepository 부원 저장소
     */
    public MemberPromotionScheduler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 매년 3월 2일 02시에 부원의 학년을 승급하고 5학년 이상 부원을 삭제합니다.
     */
    @Scheduled(cron = "0 0 2 2 3 *", zone = "Asia/Seoul")
    @Transactional
    public void promoteAndCleanup() {
        List<Member> all = memberRepository.findAll();
        LocalDateTime now = LocalDateTime.now();
        for (Member m : all) {
            LocalDateTime last = m.getLastPromotionAt() != null ? m.getLastPromotionAt() : m.getRegisteredAt();
            if (last == null) last = m.getRegisteredAt();
            if (last == null) continue; // can't compute

            int years = Period.between(last.toLocalDate(), LocalDate.now()).getYears();
            if (years <= 0) continue;

            int newGrade = m.getGrade() + years;
            if (newGrade >= 5) {
                log.info("Removing member id={} name={} grade->{} (>=5)", m.getMemberId(), m.getName(), newGrade);
                memberRepository.delete(m);
            } else {
                m.promoteByYears(years, now);
                memberRepository.save(m);
                log.info("Promoted member id={} name={} by {} years to grade={}", m.getMemberId(), m.getName(), years, m.getGrade());
            }
        }
    }
}
