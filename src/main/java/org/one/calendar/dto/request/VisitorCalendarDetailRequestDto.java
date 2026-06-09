package org.one.calendar.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "방문자 캘린더 날짜별 조회 요청 dto")
@Getter
@AllArgsConstructor
public class VisitorCalendarDetailRequestDto {
    @Schema(description = "연도", example = "2026")
    @NotNull(message = "조회할 연도를 선택해주세요.")
    @Min(value = 2000, message = "2000년 이후부터 조회 가능합니다.")
    @Max(value = 2100, message = "2100년 이전까지만 조회 가능합니다.")
    private Integer year;

    @Schema(description = "월", example = "5")
    @NotNull(message = "조회할 월을 선택해주세요.")
    @Min(value = 1, message = "월은 1 이상이어야 합니다.")
    @Max(value = 12, message = "월은 12 이하이어야 합니다.")
    private Integer month;
}
