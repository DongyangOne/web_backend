package org.one.applicant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.applicant.dto.request.ApplyRequestDto;
import org.one.applicant.service.VisitorApplicantMemberService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "ApplicantMember(visitor)", description = "신청 부원 관리 (방문자 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/visitor/applicantMembers")
public class VisitorApplicantMemberController {
    private final VisitorApplicantMemberService visitorApplicantMemberService;

    /**
     * 모집 신청 api
     *
     * api 요청 예시 : POST /api/v1/visitor/applicantMembers
     *
     * @param
     */
    @ApiErrorExceptions({ErrorCode.INVALID_INPUT})
    @Operation(summary = "모집 신청", description = "모집을 신청합니다.")
    @SecurityRequirements()
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> apply(@RequestBody @Valid ApplyRequestDto requestDto) {
        visitorApplicantMemberService.applyMember(requestDto);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
