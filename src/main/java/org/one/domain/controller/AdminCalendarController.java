package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.CalendarSaveRequestDto;
import org.one.domain.dto.response.CalendarResponseDto;
import org.one.domain.service.AdminCalendarService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 캘린더 일정 API 요청을 받고 서비스 계층으로 위임합니다.
 */
@Tag(name = "Admin - Calendar", description = "관리자 캘린더 일정 관리")
@RestController
@RequestMapping("/api/v1/admin/calendar")
@RequiredArgsConstructor
public class AdminCalendarController {

	private final AdminCalendarService adminCalendarService;

	/**
	 * 캘린더 일정을 생성합니다.
	 *
	 * @param request 생성 요청 DTO
	 * @return 생성된 일정 응답
	 */
	@Operation(summary = "캘린더 일정 생성", description = "새로운 캘린더 일정을 등록합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@PostMapping
	public ResponseEntity<ApiResponse<CalendarResponseDto>> save(
			@RequestBody @Valid CalendarSaveRequestDto request) {
		CalendarResponseDto response = adminCalendarService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}
}
