package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로젝트 수정 요청 DTO입니다.
 *
 * <p>새 사진 추가 시 먼저 {@code GET /api/v1/files/upload-url?type=project} 로 Presigned URL을 발급받아
 * 파일을 MinIO에 직접 업로드한 뒤, 응답의 objectKey 목록을 newPhotoKeys에 담아 전달합니다.
 */
@Schema(description = "프로젝트 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateRequestDto {

	@Schema(description = "년도", example = "2026")
	@NotBlank(message = "년도를 입력해주세요.")
	private String year;

	@Schema(description = "프로젝트명", example = "ONE 웹 서비스")
	@NotBlank(message = "프로젝트명을 입력해주세요.")
	private String projectName;

	@Schema(description = "수상 내역", example = "최우수상")
	private String award;

	@Schema(description = "해당 연도 활동 내역", example = "2023 하계 MT · 정기 세미나 및 튜터링 운영")
	private String activity;

	@Schema(description = "프로젝트 시작일", example = "2026-03-01")
	@NotNull(message = "시작일을 입력해주세요.")
	private LocalDate startDate;

	@Schema(description = "프로젝트 종료일", example = "2026-06-30")
	@NotNull(message = "종료일을 입력해주세요.")
	private LocalDate endDate;

	@Schema(description = "팀원 수", example = "6")
	@NotNull(message = "팀원 수를 입력해주세요.")
	@Positive(message = "팀원 수는 1명 이상이어야 합니다.")
	private Integer participantCount;

	@Schema(description = "기술 스택 목록", example = "[\"React\", \"Spring Boot\", \"MySQL\"]")
	@NotEmpty(message = "기술 스택을 입력해주세요.")
	private List<String> techStacks;

	@Schema(description = "프로젝트 소개")
	private String description;

	@Schema(description = "유지할 기존 사진 ID 목록 (생략 또는 빈 배열이면 기존 사진 전체 삭제)", example = "[1, 3]")
	private List<Long> keepPhotoIds;

	@Schema(description = "새로 추가할 사진 objectKey 목록 (Presigned URL 발급 후 업로드 완료한 키)",
			example = "[\"projects/550e8400-e29b-41d4-a716-446655440000\"]")
	private List<String> newPhotoKeys;
}
