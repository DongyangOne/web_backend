package org.one.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.one.member.dto.ApplicantMemberListRequestDto;
import org.one.member.dto.ApplicantMemberListResponseDto;
import org.one.member.service.ApplicantMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "ApplicantMember", description = "신청 부원 관리 (관리자 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/applicantMembers")
public class ApplicantMemberController {
    private final ApplicantMemberService applicantMemberService;


    /**
     * 신청 부원 조회 api
     *
     * api 요청 예시 : GET /api/v1/applicantMembers
     *
     * 응답 데이터 : 신청 부원 리스트
     */
    @ApiErrorExceptions({ErrorCode.INVALID_INPUT})
    @Operation(summary = "신청 부원 조회", description = "관리자 권한(ADMIN)이 있는 계정만 신청 부원을 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicantMemberListResponseDto>>> getApplicantMemberList(@ModelAttribute @Valid ApplicantMemberListRequestDto requestDto){
        List<ApplicantMemberListResponseDto> response = applicantMemberService.getApplicantList(requestDto);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 신청 부원 상세 정보 조회 API : 신청 부원의 상세 정보를 조회하기 위한 api
     * 요청 시, applicantMemberId를 @PathVariable로 url을 통해 전달
     *
     * api 요청 예시 : GET /api/v1/applicantMembers/{applicantMemberId}
     *
     * 응답 데이터 : 특정 신청 부원에 대한 상세 정보
     */
    @Operation(summary = "신청 부원 상세 정보 조회", description = "관리자 권한(ADMIN)이 있는 계정만 신청 부원의 상세 정보를 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{applicantMemberId}")
    public ResponseEntity<ApplicantMemberDetailResponseDto> getApplicantMemberDetail(@PathVariable Long applicantMemberId){
        ApplicantMemberDetailResponseDto responseDto= applicantMemberService.getApplicantMemberDetail(applicantMemberId);
        return ResponseEntity.ok(responseDto);
    }


}
