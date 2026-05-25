package org.one.domain.service;


import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.MemberListRequestDto;
import org.one.domain.dto.request.MemberRegisterRequestDto;
import org.one.domain.dto.response.MemberDetailResponseDto;
import org.one.domain.dto.response.MemberListResponseDto;
import org.one.domain.entity.Member;
import org.one.domain.enums.Gender;
import org.one.domain.enums.MemberStatus;
import org.one.domain.repository.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberListResponseDto> getMemberListByAdmin(MemberListRequestDto requestDto) {
        //requestDto로 설정한 sort, size 등을 바탕으로 Pageable객체를 만듦.
        Pageable pageable = requestDto.toPageable();

        //memberRepository를 이용해 모든 부원 리스트를 가져옴.
        List<Member> members = memberRepository.findAllByAdmin(pageable);

        //모든 부원 리스트를 Member(entity) -> MemberListResponseDto로 필요한 데이터만 빼서 리스트를 만듦.
        return members.stream()
                .map(MemberListResponseDto::new)    //(member -> new MemberListResponseDto(member))
                .toList();
    }

    /**
     * 새로운 부원을 추가함
     * Param : MemberRegisterRequestDto 부원 추가 dto
     */
    @Transactional
    public void registerMember(MemberRegisterRequestDto requestDto){
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
                .orElseThrow(() -> new IllegalArgumentException("부원을 찾을 수 없습니다."));
        return new MemberDetailResponseDto(member);
    }





}
