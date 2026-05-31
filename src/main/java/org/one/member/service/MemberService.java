package org.one.member.service;


import lombok.RequiredArgsConstructor;
import org.one.auth.repository.AdminRepository;
import org.one.global.enums.ErrorCode;
import org.one.global.exception.BusinessException;
import org.one.member.domain.Member;
import org.one.member.dto.MemberListRequestDto;
import org.one.member.dto.MemberListResponseDto;
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
}
