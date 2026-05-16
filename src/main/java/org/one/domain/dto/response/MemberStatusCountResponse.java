package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.one.domain.enums.MemberStatus;

/**
 * 부원 상태별 집계 응답 DTO입니다.
 *
 * @param status 부원 상태
 * @param count 해당 상태 인원 수
 */
@Schema(description = "부원 상태별 집계")
public record MemberStatusCountResponse(
		@Schema(description = "부원 상태", example = "ACTIVE")
		MemberStatus status,

		@Schema(description = "인원 수", example = "12")
		long count
) {}
