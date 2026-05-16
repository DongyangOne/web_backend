package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * 프로젝트 수정 요청 DTO입니다.
 * 사진은 별도 엔드포인트(/photos)로 관리합니다.
 *
 * @param projectName 프로젝트명
 * @param participantCount 참여 인원
 * @param description 프로젝트 설명
 * @param priority 노출 순서
 */
@Schema(description = "프로젝트 수정 요청")
public record ProjectUpdateRequest(
		@Schema(description = "프로젝트명", example = "ONE 웹사이트")
		@NotBlank(message = "프로젝트명을 입력해주세요.")
		@Size(max = 100, message = "프로젝트명은 100자 이하여야 합니다.")
		String projectName,

		@Schema(description = "참여 인원", example = "6")
		@NotNull(message = "참여 인원을 입력해주세요.")
		@Positive(message = "참여 인원은 1 이상이어야 합니다.")
		Integer participantCount,

		@Schema(description = "프로젝트 설명", example = "동아리 공식 웹사이트 개발 프로젝트입니다.")
		String description,

		@Schema(description = "노출 순서 (오름차순)", example = "0")
		@NotNull(message = "노출 순서를 입력해주세요.")
		Integer priority
) {}
