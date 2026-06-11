package org.one.member.service;


import lombok.RequiredArgsConstructor;
import org.one.auth.repository.AdminRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.global.pagination.ResponsePagingDto;
import org.one.member.domain.Member;
import org.one.member.dto.request.MemberListRequestDto;
import org.one.member.dto.request.MemberRegisterRequestDto;
import org.one.member.dto.request.MemberUpdateRequestDto;
import org.one.member.dto.response.MemberDetailResponseDto;
import org.one.member.dto.response.MemberListResponseDto;
import org.one.member.enums.MemberStatus;
import org.one.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final AdminRepository adminRepository;

    /**
     * 모든 부원을 조회함.
     * Param : MemberListRequestDto 부원 조회 dto
     */
    public ResponsePagingDto<MemberListResponseDto> getMemberListByAdmin(MemberListRequestDto requestDto) {
        Pageable pageable = requestDto.toPageable();

        Page<Member> memberPage = memberRepository.findAll(pageable);

        Page<MemberListResponseDto> dtoPage = memberPage.map(MemberListResponseDto::from);

        return ResponsePagingDto.from(dtoPage);
    }

    /**
     * 새로운 부원을 추가함
     * Param : MemberRegisterRequestDto 부원 추가 dto
     */
    @Transactional
    public void registerMember(MemberRegisterRequestDto requestDto) {
        //학번 중복 시 예외처리
        if (memberRepository.existsByStudentId(requestDto.getStudentId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_STUDENT_ID);
        }

        //전화번호 중복 시 예외처리
        if (memberRepository.existsByPhoneNumber(requestDto.getPhoneNum())) {
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
    public MemberDetailResponseDto getMemberDetail(Long memberId) {
        if (memberId == null || memberId <= 0) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        return MemberDetailResponseDto.from(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto requestDto) {
        if (memberId == null || memberId <= 0) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        //중복 값 예외처리(학번, 전화번호)
        if (!member.getStudentId().equals(requestDto.getStudentId())) {
            if (memberRepository.existsByStudentId(requestDto.getStudentId())) {
                throw new BusinessException(ErrorCode.DUPLICATE_STUDENT_ID);
            }
        }
        if (!member.getPhoneNumber().equals(requestDto.getPhoneNum())) {
            if (memberRepository.existsByPhoneNumber(requestDto.getPhoneNum())) {
                throw new BusinessException(ErrorCode.DUPLICATE_PHONE_NUMBER);
            }
        }

        member.updateInfo(requestDto.getName(), requestDto.getStudentId(), requestDto.getPhoneNum(), requestDto.getGrade(), requestDto.getAge());
    }

    /**
     * 요청 값(memberIds)에 들어있는 id를 가진 데이터들을 삭제
     * Param : memberIds
     */
    @Transactional
    public void deleteMembers(List<Long> memberIds) {
        //리스트 내 중복값 있을 경우 예외처리
        Set<Long> uniqueIds = new HashSet<>(memberIds);
        if (memberIds.size() != uniqueIds.size()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }
        //리스트 내 id 중 db에 없는 값이 하나라도 있을 경우 예외처리
        List<Member> existingMembers = memberRepository.findAllById(uniqueIds);
        if (existingMembers.size() != uniqueIds.size()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        memberRepository.deleteAllByIdInBatch(memberIds);
    }


}
