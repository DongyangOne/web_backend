package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.MemberListRequestDto;
import org.one.domain.dto.response.MemberListResponseDto;
import org.one.domain.service.MemberListService;
import org.one.global.pagination.ResponsePagingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Member", description = "부원 명부 관리 (관리자 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberListService memberListService;

    /**
     * 명부 전체 조회 API
     * 요청 시, 선택적으로 page관련 설정(정렬 등)
     *
     * 예시 : GET /api/v1/members?page=1&size=10&sort=...
     *
     * 응답 데이터 : 전체 member의 명부리스트
     */
    @Operation(summary = "명부 전체 조회", description = "관리자 권한(ADMIN)이 있는 계정만 전체 부원 명부를 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<MemberListResponseDto>> getMemberList(@ModelAttribute MemberListRequestDto requestDto){

        //
        List<MemberListResponseDto> response = memberListService.getMemberListByAdmin(requestDto);

        return ResponseEntity.ok(response);
    }
}
