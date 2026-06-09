package org.one.applicant.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.one.applicant.enums.Gender;

import java.time.LocalDate;

@Schema(description = "모집 신청 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplyRequestDto {
    @Schema(description = "신청 부원 이름", example = "홍길동")
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 8, message = "이름은 2~8자 사이로 입력해주세요.")
    private String name;

    @Schema(description = "신청 부원 학과", example = "웹응용소프트웨어공학과")
    @NotBlank(message = "학과를 선택해주세요.")
    @Size(min = 3, max = 11, message = "학과는 3~11자 사이로 입력해주세요.")
    private String department;

    @Schema(description = "신청 부원 학번", example = "20991234")
    @NotBlank(message = "학번을 입력해주세요.")
    @Pattern(regexp = "^\\d{8}$", message = "학번은 8자리의 숫자만 입력 가능합니다.")
    private String studentId;

    @Schema(description = "신청 부원 생년월일", example = "2001-01-01")
    @NotNull(message = "생년월일을 입력해주세요.")
    @Past(message = "생년월일은 과거의 날짜여야 합니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-M-d", timezone = "Asia/Seoul")
    private LocalDate birthday;

    @Schema(description = "신청 부원 학년", example = "3")
    @NotNull(message = "학년을 입력해주세요.")
    @Min(value = 1, message = "학년은 1학년 이상이어야 합니다.")
    @Max(value = 4, message = "학년은 4학년 이하이어야 합니다.")
    private Integer grade;

    @Schema(description = "신청 부원 전화번호", example = "010-1111-2222")
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^010-?\\d{3,4}-?\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678 또는 01012345678)")
    private String phoneNumber;

    @Schema(description = "신청 부원 성별", example = "FEMALE", allowableValues = {"MALE", "FEMALE"})
    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    @Schema(description = "신청 부원 지원 동기", example = "웹 개발에 관심이 많아 실무적인 프로젝트 경험을 쌓고 싶어 지원했습니다.")
    @NotBlank(message = "지원동기를 입력해주세요.")
    @Size(min = 1, max = 500, message = "지원동기를 500자 이내로 입력해주세요.")
    private String motivation;

    @Schema(description = "신청 부원 기술 스택", example = "Java, Spring")
    @NotBlank(message = "사용 가능한 기술 스택을 입력해주세요.")
    private String techStack;

    @Schema(description = "신청 부원 희망활동", example = "프론트엔드 UI/UX 개발")
    @NotBlank(message = "동아리내에서 희망하는 활동을 입력해주세요.")
    private String desiredActivity;

    @Schema(description = "신청 부원 마지막으로 하고 싶은 말", example = "잘 부탁드립니다.")
    @NotBlank(message = "마지막으로 하고 싶은 말을 입력해주세요.")
    @Size(min = 1, max = 500, message = "마지막으로 하고 싶은 말을 500자 이내로 입력해주세요.")
    private String finalWords;

    @Schema(description = "개인정보 수집 동의 여부", example = "TRUE", allowableValues = {"TRUE", "FALSE"})
    @NotNull(message = "개인정보 수집 동의 여부를 선택해주세요.")
    private Boolean privacyConsent;
}