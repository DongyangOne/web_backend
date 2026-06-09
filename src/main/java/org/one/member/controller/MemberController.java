package org.one.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.one.member.dto.*;
import org.one.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member", description = "부원 명부 관리 (관리자 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/members")
@Validated
public class MemberController {
    private final MemberService memberService;

    /**
     * 명부 전체 조회 API
     * 요청 시, 선택적으로 page관련 설정(정렬 등)
     *
     * api 요청 예시 : GET /api/v1/admin/members?page=1&size=10&sort=...
     *
     * 응답 데이터 : 전체 member의 명부리스트
     */
    @ApiErrorExceptions({ErrorCode.INVALID_INPUT})
    @Operation(summary = "명부 전체 조회", description = "관리자 권한(ADMIN)이 있는 계정만 전체 부원 명부를 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberListResponseDto>>> getMemberList(
            @ModelAttribute @Valid MemberListRequestDto requestDto) {

        List<MemberListResponseDto> response = memberService.getMemberListByAdmin(requestDto);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 부원 등록 API
     * 요청 시, requestBody를 이용 MemberRegisterRequestDto 필드 입력
     *
     * api 요청 예시 : POST /api/v1/admin/members
     *
     * requestBody 예시
     * "name" : "aa",
     * "grade" : 1,
     * "studentId" : "20991111",
     * "age" : 22,
     * "phoneNum" : "010-1111-2222"
     *
     * 응답 데이터 : x
     */
    @ApiErrorExceptions({ErrorCode.DUPLICATE_PHONE_NUMBER, ErrorCode.DUPLICATE_STUDENT_ID, ErrorCode.INVALID_INPUT})
    @Operation(summary = "부원 등록", description = "관리자 권한(ADMIN)이 있는 계정만 전체 부원 명부를 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerMember(@RequestBody @Valid MemberRegisterRequestDto requestDto) {
        //등록 정보를 service로 넘겨 부원 등록 진행
        memberService.registerMember(requestDto);

        //오류없이 넘어왔을 경우 성공 처리
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    /**
     * 특정 부원 정보 조회 API : 부원 수정 시 정보를 불러오기 위한 api
     * 요청 시, memberId를 @PathVariable로 url을 통해 전달
     *
     * api 요청 예시 : GET /api/v1/admin/members/{memberId}
     *
     * 응답 데이터 : 특정 부원에 대한 정보
     */
    @ApiErrorExceptions({ErrorCode.MEMBER_NOT_FOUND, ErrorCode.INVALID_INPUT})
    @Operation(summary = "부원 정보 가져오기", description = "관리자 권한(ADMIN)이 있는 계정만 부원 상세 정보를 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<MemberDetailResponseDto>> getMemberDetail(@PathVariable Long memberId) {
        MemberDetailResponseDto responseDto = memberService.getMemberDetail(memberId);
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    /**
     * 부원 정보 수정 api
     * 요청 시,
     *
     * @PathVariable와 @requestBody를 통해 memberId와 MemberUpdateReqeustDto전달
     *
     * api 요청 예시 : PATCH /api/v1/admin/members/{memberId}
     *
     * 응답 데이터 : 수정 완료 메시지
     */
    @ApiErrorExceptions({ErrorCode.MEMBER_NOT_FOUND, ErrorCode.INVALID_INPUT, ErrorCode.DUPLICATE_PHONE_NUMBER, ErrorCode.DUPLICATE_STUDENT_ID})
    @Operation(summary = "부원 수정", description = "관리자 권한(ADMIN)이 있는 계정만 특정 부원의 정보를 일부 수정할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> updateMember(
            @PathVariable Long memberId,
            @Valid @RequestBody MemberUpdateRequestDto requestDto) {
        memberService.updateMember(memberId, requestDto);

        return ResponseEntity.ok(null);
    }

    /**
     * 부원 일괄 삭제 API
     * 요청 시, URL 쿼리 파라미터를 통해 memberIds 리스트를 전달 (HTTP Body 사용 안 함)
     *
     * API 요청 예시 : DELETE /api/v1/admin/members?memberIds=12&memberIds=15
     *
     * 응답 데이터 : 공통 성공 응답 포맷 (data: null)
     */
    @ApiErrorExceptions({ErrorCode.MEMBER_NOT_FOUND, ErrorCode.INVALID_INPUT, ErrorCode.DUPLICATE_SELECT_MEMBER})
    @Operation(summary = "부원 일괄 삭제", description = "관리자 권한(ADMIN)이 있는 계정만 여러 부원을 한 번에 삭제할 수 있습니다. 성공 시 빈 데이터를 포함한 공통 응답 포맷을 반환합니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteMembers(
            @RequestParam("memberIds")
            @NotEmpty(message = "삭제할 부원(들)의 id를 입력해주세요.")
            @Schema(description = "삭제할 부원들의 id", example = "5")
            List<@NotNull @Positive(message = "올바르지 않은 부원의 id가 포함되어있습니다.") Long> memberIds
    ) {

        memberService.deleteMembers(memberIds);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
