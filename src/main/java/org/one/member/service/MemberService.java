package org.one.member.service;


import lombok.RequiredArgsConstructor;
import org.one.auth.repository.AdminRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.member.domain.Member;
import org.one.member.dto.MemberDetailResponseDto;
import org.one.member.dto.MemberListRequestDto;
import org.one.member.dto.MemberListResponseDto;
import org.one.member.dto.MemberRegisterRequestDto;
import org.one.member.enums.MemberStatus;
import org.one.member.repository.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    public List<MemberListResponseDto> getMemberListByAdmin(MemberListRequestDto requestDto) {
        //requestDto로 설정한 sort, size 등을 바탕으로 Pageable객체를 만듦.
        Pageable pageable = requestDto.toPageable();

        //memberRepository를 이용해 모든 부원 리스트를 가져옴.
        List<Member> members = memberRepository.findAllByAdmin(pageable);

        //모든 부원 리스트를 Member(entity) -> MemberListResponseDto로 필요한 데이터만 빼서 리스트를 만듦.
        return members.stream()
                .map(MemberListResponseDto::from)
                .toList();
    }

    /**
     * 새로운 부원을 추가함
     * Param : MemberRegisterRequestDto 부원 추가 dto
     */
    @Transactional
    public void registerMember(MemberRegisterRequestDto requestDto){
        //학번 중복 시 예외처리
        if(memberRepository.existsByStudentId(requestDto.getStudentId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_STUDENT_ID);
        }

        //전화번호 중복 시 예외처리
        if(memberRepository.existsByPhoneNumber(requestDto.getPhoneNum())) {
            throw new BusinessException(ErrorCode.DUPLICATE_PHONE_NUMBER);
        }

        //요청데이터인 dto를 사용하여 새로운 Member엔티티 설정
        Member member = Member.builder()
                .name(requestDto.getName())
                .grade(requestDto.getGrade())
                .studentId(requestDto.getStudentId())
                .age(requestDto.getAge())
                .phoneNumber(requestDto.getPhoneNum())
                .status(MemberStatus.ACTIVE) //status는 기본 값(활동중)
                .build();

        //새로 생성한 부원 객체를 save(insert)해줌.
        memberRepository.save(member);
    }

    /**
     * 요청값(memberId)를 통해 해당 부원의 정보를 불러옴.
     * Param : memberId
     * return : MemberDetailResponseDto
     */
    public MemberDetailResponseDto getMemberDetail(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberDetailResponseDto.from(member);
    }
}
