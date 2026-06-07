package org.one.main.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.one.main.dto.response.VisitorMainResponseDto;
import org.one.main.service.VisitorMainService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "main(visitor)", description = "메인 페이지(방문자 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/visitor/main")
public class VisitorMainController {
    private final VisitorMainService visitorMainService;

    /**
     * 메인 페이지(방문자) API
     *
     * api 요청 예시 : GET /api/v1/visitor/main
     *
     * 응답 데이터 : 메인페이지 데이터
     */
    @ApiErrorExceptions({ErrorCode.MAINPAGE_CONFIG_NOT_FOUND})
    @Operation(summary = "메인 페이지", description = "메인페이지 데이터를 불러옵니다.")
    @SecurityRequirements()
    @GetMapping
    public ResponseEntity<ApiResponse<VisitorMainResponseDto>> getMainPage(){
        VisitorMainResponseDto responseDto = visitorMainService.getMainPage();
        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

}
