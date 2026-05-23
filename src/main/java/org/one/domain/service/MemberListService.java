package org.one.domain.service;


import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.MemberListRequestDto;
import org.one.domain.dto.response.MemberListResponseDto;
import org.one.domain.entity.Member;
import org.one.domain.repository.MemberRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberListService {
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
}
