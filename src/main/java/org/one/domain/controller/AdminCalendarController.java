package org.one.domain.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.one.domain.dto.request.CalendarSaveRequestDto;
import org.one.domain.dto.request.CalendarUpdateRequestDto;
import org.one.domain.dto.response.CalendarMonthlyResponseDto;
import org.one.domain.dto.response.CalendarResponseDto;
import org.one.domain.service.AdminCalendarService;
import org.one.global.annotation.ApiErrorExceptions;
import org.one.global.dto.ApiResponse;
import org.one.global.enums.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자 캘린더 일정 API 요청을 받고 서비스 계층으로 위임합니다.
 */
@Tag(name = "Admin - Calendar", description = "관리자 캘린더 일정 관리")
@RestController
@RequestMapping("/api/v1/admin/calendar")
@RequiredArgsConstructor
@Validated
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

	/**
	 * 특정 연도의 캘린더 일정을 월별 그룹으로 조회합니다.
	 *
	 * @param year 연도 (필수)
	 * @return 월별 그룹핑된 일정 목록
	 */
	@Operation(summary = "캘린더 년별 조회", description = "해당 연도의 일정을 월별로 묶어 조회합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@GetMapping
	public ResponseEntity<ApiResponse<List<CalendarMonthlyResponseDto>>> findAllByYear(
			@RequestParam Integer year) {
		List<CalendarMonthlyResponseDto> response = adminCalendarService.findAllByYear(year);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	/**
	 * 특정 연월의 캘린더 일정을 조회합니다.
	 *
	 * @param year 연도 (필수)
	 * @param month 월 (필수)
	 * @return 해당 월의 일정 목록
	 */
	@Operation(summary = "캘린더 월별 조회", description = "해당 연월의 일정을 조회합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@GetMapping("/month")
	public ResponseEntity<ApiResponse<List<CalendarResponseDto>>> findAllByMonth(
			@RequestParam Integer year,
			@RequestParam Integer month) {
		List<CalendarResponseDto> response = adminCalendarService.findAllByMonth(year, month);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	/**
	 * 캘린더 일정을 수정합니다.
	 *
	 * @param calendarId 수정할 일정 ID
	 * @param request 수정 요청 DTO
	 * @return 수정된 일정 응답
	 */
	@Operation(summary = "캘린더 일정 수정", description = "지정한 캘린더 일정의 내용을 수정합니다.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.RESOURCE_NOT_FOUND, ErrorCode.INTERNAL_SERVER_ERROR})
	@PatchMapping("/{calendarId}")
	public ResponseEntity<ApiResponse<CalendarResponseDto>> update(
			@PathVariable Long calendarId,
			@RequestBody @Valid CalendarUpdateRequestDto request) {
		CalendarResponseDto response = adminCalendarService.update(calendarId, request);
		return ResponseEntity.ok(ApiResponse.success(response));
	}

	/**
	 * 선택한 캘린더 일정을 일괄 삭제합니다.
	 *
	 * @param calendarIds 삭제할 일정 ID 목록
	 * @return 삭제 완료 응답
	 */
	@Operation(summary = "캘린더 일정 삭제", description = "선택한 캘린더 일정을 일괄 삭제합니다. 멀티 선택 지원.")
	@ApiErrorExceptions({ErrorCode.INVALID_INPUT, ErrorCode.UNAUTHORIZED, ErrorCode.FORBIDDEN, ErrorCode.INTERNAL_SERVER_ERROR})
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> delete(
			@RequestParam @NotEmpty(message = "삭제할 일정을 선택해주세요.") List<Long> calendarIds) {
		adminCalendarService.delete(calendarIds);
		return ResponseEntity.ok(ApiResponse.success(null));
	}
}
