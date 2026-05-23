package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.domain.entity.Recruitment;

/**
 * 모집 공고 조회 응답 DTO입니다.
 */
@Schema(description = "모집 공고 조회 응답")
@Getter
@Builder
@AllArgsConstructor
public class RecruitmentResponseDto {

	@Schema(description = "모집 대상", example = "웹응용소프트웨어공학과 1~2학년")
	private String target;

	@Schema(description = "지원 분야", example = "프론트엔드, 백엔드")
	private String field;

	@Schema(description = "모집 시작일", example = "2026-05-10")
	private LocalDate recruitmentStart;

	@Schema(description = "모집 종료일", example = "2026-05-30")
	private LocalDate recruitmentEnd;

	@Schema(description = "면접 시작일", example = "2026-06-01")
	private LocalDate interviewStart;

	@Schema(description = "면접 종료일", example = "2026-06-10")
	private LocalDate interviewEnd;

	@Schema(description = "합격자 발표일", example = "2026-06-17")
	private LocalDate notificationDate;

	@Schema(description = "현재 모집 중 여부")
	private boolean recruiting;

	/**
	 * Recruitment 엔티티에서 응답 DTO를 생성합니다.
	 *
	 * @param recruitment 모집 공고 엔티티
	 * @param isRecruiting 현재 모집 중 여부
	 * @return 모집 공고 응답 DTO
	 */
	public static RecruitmentResponseDto from(Recruitment recruitment, boolean isRecruiting) {
		return RecruitmentResponseDto.builder()
				.field(recruitment.getField())
				.target(recruitment.getTarget())
				.recruitmentStart(recruitment.getRecruitmentStart())
				.recruitmentEnd(recruitment.getRecruitmentEnd())
				.interviewStart(recruitment.getInterviewStart())
				.interviewEnd(recruitment.getInterviewEnd())
				.notificationDate(recruitment.getNotificationDate())
				.recruiting(isRecruiting)
				.build();
	}
}
