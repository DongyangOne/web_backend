package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주요활동 카드 수정 요청 DTO입니다.
 */
@Schema(description = "주요활동 카드 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityCardUpdateRequestDto {

	@Schema(description = "카드 제목", example = "웹 개발 스터디")
	@NotBlank
	private String title;

	@Schema(description = "카드 내용", example = "매주 토요일 진행합니다.")
	@NotBlank
	private String content;
}
