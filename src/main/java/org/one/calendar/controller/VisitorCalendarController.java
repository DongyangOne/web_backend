package org.one.calendar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.calendar.dto.request.VisitorCalendarDetailRequestDto;
import org.one.calendar.dto.response.CalendarResponseDto;
import org.one.calendar.repository.CalendarScheduleRepository;
import org.one.calendar.service.VisitorCalendarService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Calendar(visitor)", description = "캘린더 관련 작업을 수행합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/visitor/calendar")
public class VisitorCalendarController {
    private final VisitorCalendarService visitorCalendarService;

    /**
     * 캘린더 월별 조회 api
     *
     * @param VisitorCalendarDetailRequestDto 캘린더 일정 조회 요청 dto
     * @return 해당 월의 일정 목록
     */
    @Operation(summary = "캘린더 월별 조회", description = "해당 연월의 일정을 조회합니다.")
    @ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
    @SecurityRequirements()
    @GetMapping("/month")
    public ResponseEntity<ApiResponse<List<CalendarResponseDto>>> getMonthScheduleList(@ModelAttribute @Valid VisitorCalendarDetailRequestDto requestDto) {
        List<CalendarResponseDto> response = visitorCalendarService.getMonthScheduleList(requestDto.getYear(), requestDto.getMonth());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
