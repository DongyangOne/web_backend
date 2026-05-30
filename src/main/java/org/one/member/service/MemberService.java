package org.one.member.service;


import lombok.RequiredArgsConstructor;
import org.one.auth.repository.AdminRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.member.domain.Member;
import org.one.member.dto.MemberListRequestDto;
import org.one.member.dto.MemberListResponseDto;
import org.one.member.dto.MemberRegisterRequestDto;
import org.one.member.enums.MemberStatus;
import org.one.member.repository.MemberRepository;
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
    private final AdminRepository adminRepository;

    public List<MemberListResponseDto> getMemberListByAdmin(MemberListRequestDto requestDto) {
        //size가 비정상적일 경우 예외처리
        if(requestDto.getSize() <= 0 ||requestDto.getSize() > 100){
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        //허용되지 않는 정렬 값을 넣을 경우 예외처리
        String sort = requestDto.getSort();
        if(!"createdAt".equals(sort) && !"grade".equals(sort)){
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        //오름차순 내림차순 외 예외처리
        String direction = requestDto.getDirection();
        if(!"ASC".equalsIgnoreCase(direction) && !"DESC".equalsIgnoreCase(direction)){
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

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
     * 요청값(memberId)를 통해 해당 부원의 상세 정보를 불러옴.
     * Param : memberId
     * return : MemberDetailResponseDto
     */
    public MemberDetailResponseDto getMemberDetail(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("부원을 찾을 수 없습니다."));
        return new MemberDetailResponseDto(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto requestDto){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 부원입니다."));

        member.updateInfo(requestDto.getName(), requestDto.getStudentId(), requestDto.getPhoneNum(), requestDto.getGrade(), requestDto.getAge());
    }





}
