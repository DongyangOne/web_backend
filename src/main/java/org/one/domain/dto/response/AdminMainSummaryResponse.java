package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 관리자 메인 요약 조회 응답 DTO입니다.
 *
 * @param logoUrl 로고 URL
 * @param description 소개 문구
 * @param isRecruiting 현재 모집 중 여부 (날짜 범위로 자동 계산)
 * @param recruitmentStart 모집 시작일
 * @param recruitmentEnd 모집 종료일
 * @param totalMembers 전체 부원 수
 * @param memberStatusCounts 상태별 부원 수
 * @param updatedAt 메인 설정 마지막 수정 시각
 */
@Schema(description = "관리자 메인 요약 응답")
public record AdminMainSummaryResponse(
	@Schema(description = "로고 이미지 URL", example = "https://cdn.example.com/logo.png")
	String logoUrl,

	@Schema(description = "소개 문구", example = "ONE 동아리 소개 텍스트...")
	String description,

	@Schema(description = "현재 모집 중 여부", example = "true")
	boolean isRecruiting,

	@Schema(description = "모집 시작일", example = "2026-03-04")
	LocalDate recruitmentStart,

	@Schema(description = "모집 종료일", example = "2026-03-20")
	LocalDate recruitmentEnd,

	@Schema(description = "전체 부원 수", example = "35")
	long totalMembers,

	@Schema(description = "부원 상태별 인원")
	List<MemberStatusCountResponse> memberStatusCounts,

	@Schema(description = "메인 설정 마지막 수정 시각", example = "2026-05-14T12:00:00")
	LocalDateTime updatedAt
) {}
