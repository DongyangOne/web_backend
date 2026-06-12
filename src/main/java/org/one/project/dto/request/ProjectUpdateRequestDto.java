package org.one.project.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로젝트 수정 요청 DTO입니다.
 */
@Schema(description = "프로젝트 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequestDto {

	@Schema(description = "년도", example = "2026")
	@NotBlank(message = "년도를 입력해주세요.")
	@Size(max = 20, message = "년도는 20자 이내로 입력해주세요.")
	private String year;

	@Schema(description = "프로젝트명", example = "ONE 웹 서비스")
	@NotBlank(message = "프로젝트명을 입력해주세요.")
	@Size(max = 100, message = "프로젝트명은 100자 이내로 입력해주세요.")
	private String projectName;

	@Schema(description = "수상 내역", example = "최우수상")
	@Size(max = 255, message = "수상 내역은 255자 이내로 입력해주세요.")
	private String award;

	@Schema(description = "해당 연도 활동 내역", example = "2023 하계 MT · 정기 세미나 및 튜터링 운영")
	@Size(max = 1000, message = "활동 내역은 1000자 이내로 입력해주세요.")
	private String activity;

	@Schema(description = "프로젝트 시작일", example = "2026-03-01")
	@NotNull(message = "시작일을 입력해주세요.")
	private LocalDate startDate;

	@Schema(description = "프로젝트 종료일", example = "2026-06-30")
	@NotNull(message = "종료일을 입력해주세요.")
	private LocalDate endDate;

	@Schema(description = "팀원 수", example = "6")
	@NotNull(message = "팀원 수를 입력해주세요.")
	@Positive(message = "팀원 수는 양수여야 합니다.")
	private Integer participantCount;

	@Schema(description = "기술 스택 목록", example = "[\"React\", \"Spring Boot\", \"MySQL\"]")
	@NotEmpty(message = "기술 스택을 입력해주세요.")
	private List<@NotBlank(message = "기술 스택명은 비어 있을 수 없습니다.") String> techStacks;

	@Schema(description = "프로젝트 소개")
	@Size(max = 1000, message = "프로젝트 소개는 1000자 이내로 입력해주세요.")
	private String description;

	@Schema(description = "유지할 기존 사진 ID 목록 (생략 또는 빈 배열이면 기존 사진 전체 삭제)", example = "[1, 3]")
	private List<@Positive(message = "사진 ID는 양수여야 합니다.") Long> keepPhotoIds;

	@Schema(description = "새로 추가할 사진 objectKey 목록 (Presigned URL 발급 후 업로드 완료한 키)",
			example = "[\"projects/550e8400-e29b-41d4-a716-446655440000\"]")
	private List<@NotBlank(message = "사진 objectKey는 비어 있을 수 없습니다.") String> newPhotoKeys;
}
