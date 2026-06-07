package org.one.applicant.service;

import lombok.RequiredArgsConstructor;
import org.one.applicant.domain.ApplicantMember;
import org.one.applicant.dto.request.ApplyRequestDto;
import org.one.applicant.repository.ApplicantMemberRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitorApplicantMemberService {
    private final ApplicantMemberRepository applicantMemberRepository;

    /**
     * 모집 신청 처리
     *
     * @Param ApplyRequestDto 모집 신청 요청 dto
     */
    @Transactional
    public void applyMember(ApplyRequestDto requestDto) {
        String formattedPhoneNumber = requestDto.getPhoneNumber()
                .replaceAll("[^0-9]", "")
                .replaceFirst("(\\d{3})(\\d{3,4})(\\d{4})", "$1-$2-$3");
        //개인정보 수집 동의 여부 비동의시 예외처리
        if (!(requestDto.getPrivacyConsent())) {
            throw new BusinessException(ErrorCode.PRIVACY_POLICY_NOT_AGREED);
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
