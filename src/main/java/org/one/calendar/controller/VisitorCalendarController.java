package org.one.calendar.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.one.calendar.dto.request.VisitorCalendarDetailRequestDto;
import org.one.calendar.dto.response.CalendarMonthlyResponseDto;
import org.one.calendar.dto.response.CalendarResponseDto;
import org.one.calendar.repository.CalendarScheduleRepository;
import org.one.calendar.service.VisitorCalendarService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Calendar(visitor)", description = "캘린더 조회(방문자 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/visitor/calendar")
@Validated
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

    /**
     * 캘린더 특정년도 전체 일정 조회
     *
     * @param year 연도 (필수)
     * @return 월별 그룹핑된 일정 목록
     */
    @Operation(summary = "캘린더 년별 조회", description = "해당 연도의 일정을 월별로 묶어 조회합니다.")
    @ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
    @SecurityRequirements()
    @GetMapping
    public ResponseEntity<ApiResponse<List<CalendarMonthlyResponseDto>>> getYearScheduleList(
            @RequestParam
            @Schema(description = "연도", example = "2026")
            @NotNull(message = "조회할 연도를 선택해주세요.")
            @Min(value = 2000, message = "2000년 이후부터 조회 가능합니다.")
            @Max(value = 2100, message = "2100년 이전까지만 조회 가능합니다.")
            Integer year
    ) {
        List<CalendarMonthlyResponseDto> response = visitorCalendarService.getYearScheduleList(year);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}
