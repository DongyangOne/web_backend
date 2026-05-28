package org.one.domain.service;

import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ApplicantMemberListRequestDto;
import org.one.domain.dto.response.ApplicantInfoResponseDto;
import org.one.domain.dto.response.ApplicantMemberDetailResponseDto;
import org.one.domain.dto.response.ApplicantMemberListResponseDto;
import org.one.domain.entity.ApplicantMember;
import org.one.domain.repository.ApplicantMemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 신청부원 리스트를 모두 가져옴.
 * @Param ApplicantMemberListRequestDto : 페이지 설정 정보(정렬, 개수 등) 전달
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantMemberService {
    private final ApplicantMemberRepository applicantMemberRepository;

    public List<ApplicantMemberListResponseDto> getApplicantList(ApplicantMemberListRequestDto requestDto){
        Pageable pageable = requestDto.toPageable();

        List<ApplicantMember> applicantMemberList = applicantMemberRepository.findAllBy(pageable);

        //엔티티에서 dto형태로 구조를 변환하여 리스트를 만들어 반환
        return applicantMemberList.stream()
                .map(ApplicantMemberListResponseDto::new)
                .toList();
    }


    /**
     * 요청값(memberId)를 통해 해당 신청 부원의 상세 정보를 불러옴.
     * Param : applicantMemberId
     * return : ApplicantMemberDetailResponseDto
     */
    public ApplicantMemberDetailResponseDto getApplicantMemberDetail(Long applicantMemberId){
        ApplicantMember applicantMember = applicantMemberRepository.findById(applicantMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청 부원을 찾을 수 없습니다."));
        return new ApplicantMemberDetailResponseDto(applicantMember);
    }

    /**
     * 요청값(memberId)를 통해 등록에 사용할 신청 정보를 불러옴.
     * Param : applicantMemberId
     * return : ApplicantInfoResponseDto
     */
    public ApplicantInfoResponseDto getApplicantInfo(Long applicantMemberId){
        ApplicantMember applicantMember = applicantMemberRepository.findById(applicantMemberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 신청 부원을 찾을 수가 없습니다."));
        return new ApplicantInfoResponseDto(applicantMember);
    }

}
