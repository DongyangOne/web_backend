package org.one.applicant.service;

import lombok.RequiredArgsConstructor;
import org.one.applicant.domain.ApplicantMember;
import org.one.applicant.dto.request.ApplyRequestDto;
import org.one.applicant.repository.ApplicantMemberRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.recruitment.domain.Recruitment;
import org.one.recruitment.repository.RecruitmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorApplicantMemberService {
    private final ApplicantMemberRepository applicantMemberRepository;
    private final RecruitmentRepository recruitmentRepository;

    /**
     * 모집 신청 처리
     *
     * @Param ApplyRequestDto 모집 신청 요청 dto
     */
    @Transactional
    public void applyMember(ApplyRequestDto requestDto) {
        String formattedPhoneNumber = requestDto.getPhoneNumber()
                .replaceAll("[^0-9]", "")
                .replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        //개인정보 수집 동의 여부 비동의시 예외처리
        if (!(requestDto.getPrivacyConsent())) {
            throw new BusinessException(ErrorCode.PRIVACY_POLICY_NOT_AGREED);
        }

        //모집기간 내 중복 제출 예외처리
        //현재 모집 정보 불러오기
        Recruitment recruitment = recruitmentRepository.findById(1)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND));
        // LocalDate를 LocalDateTime으로 변환하여 시간 범위 지정 (시작일 00:00:00 ~ 종료일 23:59:59)
        LocalDateTime currentRecruitmentStart = recruitment.getRecruitmentStart().atStartOfDay();
        LocalDateTime currentRecruitmentEnd = recruitment.getRecruitmentEnd().atTime(23, 59, 59);
        // 현재 모집 기간 내에 동일한 학번으로 지원한 이력이 있는지 검증
        boolean isDuplicateApplication = applicantMemberRepository.existsByStudentIdAndCreatedAtBetween(
                requestDto.getStudentId(),
                currentRecruitmentStart,
                currentRecruitmentEnd
        );
        if (isDuplicateApplication) {
            throw new BusinessException(ErrorCode.ALREADY_APPLIED);
        }


        //요청데이터인 dto를 사용하여 새로운 ApplicantMember엔티티 설정
        ApplicantMember applicantMember = ApplicantMember.builder()
                .name(requestDto.getName())
                .department(requestDto.getDepartment())
                .studentId(requestDto.getStudentId())
                .birthday(requestDto.getBirthday())
                .grade(requestDto.getGrade())
                .phoneNumber(formattedPhoneNumber)
                .gender(requestDto.getGender())
                .motivation(requestDto.getMotivation())
                .techStack(requestDto.getTechStack())
                .desiredActivity(requestDto.getDesiredActivity())
                .finalWords(requestDto.getFinalWords())
                .isFirstView(true)
                .privacyConsent(true)
                .build();

        applicantMemberRepository.save(applicantMember);
    }

}
