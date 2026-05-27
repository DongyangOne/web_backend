package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.ApplicantMemberListRequestDto;
import org.one.domain.dto.response.ApplicantMemberListResponseDto;
import org.one.domain.service.ApplicantMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ApplicantMember", description = "신청 부원 관리 (관리자 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applicantmembers")
public class ApplicantMemberController {
    private final ApplicantMemberService applicantMemberService;


    /**
     * 신청 부원 조회 api
     *
     * api 요청 예시 : GET /api/v1/applicantmembers
     *
     * 응답 데이터 : 신청 부원 리스트
     */
    @Operation(summary = "신청 부원 조회", description = "관리자 권한(ADMIN)이 있는 계정만 신청 부원을 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ApplicantMemberListResponseDto>> getApplicantMemberList(){

        ApplicantMemberListRequestDto requestDto = new ApplicantMemberListRequestDto();

        List<ApplicantMemberListResponseDto> response = applicantMemberService.getApplicantList(requestDto);
        return ResponseEntity.ok(response);
    }

}
