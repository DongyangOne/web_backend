package org.one.recruitment.service;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.one.recruitment.dto.request.RecruitmentUpdateRequestDto;
import org.one.recruitment.dto.response.RecruitmentResponseDto;
import org.one.recruitment.domain.Recruitment;
import org.one.recruitment.repository.RecruitmentRepository;
import org.one.global.exception.BusinessException;
import org.one.global.enums.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 모집 공고 관련 비즈니스 로직을 처리합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AdminRecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    /**
     * 현재 모집 공고 정보를 조회합니다.
     *
     * @return 모집 공고 응답 DTO
     */
    public RecruitmentResponseDto findOne() {
        Recruitment recruitment = getOrInitRecruitment();
        return RecruitmentResponseDto.from(recruitment, computeIsRecruiting(recruitment));
    }

    /**
     * 모집 공고 정보를 수정합니다.
     *
     * @param request 수정 요청 DTO
     * @return 수정된 모집 공고 응답 DTO
     */
    public RecruitmentResponseDto update(RecruitmentUpdateRequestDto request) {
        validateDateRange(request.getRecruitmentStart(), request.getRecruitmentEnd());
        validateDateRange(request.getInterviewStart(), request.getInterviewEnd());
        validateDateRange(request.getInterviewEnd(), request.getNotificationDate());
        validateRecruitmentInterviewOrder(request.getRecruitmentEnd(), request.getInterviewStart());

        Recruitment recruitment = getOrInitRecruitment();
        recruitment.update(
                request.getTarget(),
                request.getField(),
                request.getRecruitmentStart(),
                request.getRecruitmentEnd(),
                request.getInterviewStart(),
                request.getInterviewEnd(),
                request.getNotificationDate(),
                request.getContactNumber(),
                request.getRoomLocation()
        );
        return RecruitmentResponseDto.from(recruitment, computeIsRecruiting(recruitment));
    }

    /**
     * 모집 공고 엔티티를 조회하거나, 없으면 기본값으로 초기화하여 반환합니다.
     *
     * @return 모집 공고 엔티티
     */
    private Recruitment getOrInitRecruitment() {
        return recruitmentRepository.findById(1)
                .orElseGet(() -> recruitmentRepository.save(Recruitment.singleton()));
    }

    /**
     * 시작일이 종료일보다 늦지 않은지 검증합니다.
     *
     * @param start 시작일
     * @param end   종료일
     */
    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new BusinessException(ErrorCode.INVALID_DATE_RANGE);
        }
    }

    /**
     * 면접 시작일이 모집 종료일보다 이르지 않은지 검증합니다.
     * 면접은 모집이 끝난 이후에 시작되어야 합니다.
     *
     * @param recruitmentEnd 모집 종료일
     * @param interviewStart 면접 시작일
     */
    private void validateRecruitmentInterviewOrder(LocalDate recruitmentEnd, LocalDate interviewStart) {
        if (recruitmentEnd != null && interviewStart != null && interviewStart.isBefore(recruitmentEnd)) {
            throw new BusinessException(ErrorCode.INVALID_DATE_RANGE);
        }
    }

    /**
     * 현재 날짜를 기준으로 모집 중 여부를 계산합니다.
     *
     * @param recruitment 모집 공고 엔티티
     * @return 모집 중 여부
     */
    private boolean computeIsRecruiting(Recruitment recruitment) {
        LocalDate start = recruitment.getRecruitmentStart();
        LocalDate end = recruitment.getRecruitmentEnd();
        if (start == null || end == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return !today.isBefore(start) && !today.isAfter(end);
    }
}
