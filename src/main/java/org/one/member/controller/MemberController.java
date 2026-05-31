package org.one.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.one.member.dto.MemberListRequestDto;
import org.one.member.dto.MemberListResponseDto;
import org.one.member.dto.MemberRegisterRequestDto;
import org.one.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member", description = "부원 명부 관리 (관리자 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * 명부 전체 조회 API
     * 요청 시, 선택적으로 page관련 설정(정렬 등)
     *
     * api 요청 예시 : GET /api/v1/members?page=1&size=10&sort=...
     *
     * 응답 데이터 : 전체 member의 명부리스트
     */
    @ApiErrorExceptions({ErrorCode.INVALID_INPUT})
    @Operation(summary = "명부 전체 조회", description = "관리자 권한(ADMIN)이 있는 계정만 전체 부원 명부를 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberListResponseDto>>> getMemberList(
            @ModelAttribute @Valid MemberListRequestDto requestDto){

        List<MemberListResponseDto> response = memberService.getMemberListByAdmin(requestDto);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 부원 등록 API
     * 요청 시, requestBody를 이용 MemberRegisterRequestDto 필드 입력
     *
     * api 요청 예시 : POST /api/v1/members
     *
     * requestBody 예시
     * "name" : "aa",
     * "grade" : 1,
     * "studentId" : "20991111",
     * "age" : 22,
     * "phoneNum" : "010-1111-2222"
     *
     * 응답 데이터 : 성공 메세지
     */
    @ApiErrorExceptions({ErrorCode.DUPLICATE_PHONE_NUMBER, ErrorCode.DUPLICATE_STUDENT_ID, ErrorCode.INVALID_INPUT})
    @Operation(summary = "부원 등록", description = "관리자 권한(ADMIN)이 있는 계정만 전체 부원 명부를 조회할 수 있습니다.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerMember(@RequestBody @Valid MemberRegisterRequestDto requestDto){
        //등록 정보를 service로 넘겨 부원 등록 진행
        memberService.registerMember(requestDto);

        //오류없이 넘어왔을 경우 성공 처리
        return ResponseEntity.ok(ApiResponse.success(null, "부원 등록이 성공적으로 완료되었습니다."));
    }
}
