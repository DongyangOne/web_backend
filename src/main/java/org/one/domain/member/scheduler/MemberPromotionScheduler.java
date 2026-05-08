package org.one.domain.member.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import org.one.domain.member.entity.Member;
import org.one.domain.member.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MemberPromotionScheduler {

    private static final Logger log = LoggerFactory.getLogger(MemberPromotionScheduler.class);

    private final MemberRepository memberRepository;

    public MemberPromotionScheduler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // run annually on March 2nd at 02:00 (일괄 개학일)
    @Scheduled(cron = "0 0 2 2 3 *")
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
                // delete when becomes 5학년 이상
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
