package org.one.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "부원 정보 수정 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {
    @Schema(description = "부원 이름", example = "홍길동")
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 20, message = "이름은 2~20자 사이로 입력해주세요.")
    private String name;

    @Schema(description = "부원 학년", example = "1")
    @NotNull(message = "학년을 선택해주세요.")
    @Min(value = 1, message = "학년은 1학년 이상이어야 합니다.")
    @Max(value = 4, message = "학년은 4학년 이하이어야 합니다.")
    private Integer grade;

    @Schema(description = "부원 학번", example = "20991111")
    @NotBlank(message = "학번을 입력해주세요.")
    @Pattern(regexp = "^\\d{8}$", message = "학번은 8자리의 숫자만 입력 가능합니다.")
    private String studentId;

    @Schema(description = "부원 나이", example = "22")
    @NotNull(message = "나이를 입력해주세요.")
    @Min(value = 18, message = "나이는 18세 이상이어야 합니다.")
    @Max(value = 100, message = "나이가 올바르지 않습니다.")
    private Integer age;

    @Schema(description = "부원 전화번호", example = "010-1111-2222")
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)")
    private String phoneNum;
}
