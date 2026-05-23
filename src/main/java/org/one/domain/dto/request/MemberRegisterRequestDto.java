package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "새로운 부원 등록 요청")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterRequestDto {

    @Schema(description = "부원 이름", example = "홍길동")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Schema(description = "부원 학년", example = "1")
    @NotNull(message = "학년을 선택해주세요.")
    private Integer grade;

    @Schema(description = "부원 학번", example = "20991111")
    @NotBlank(message = "학번을 입력해주세요.")
    private String studentId;

    @Schema(description = "부원 나이", example = "22")
    @NotNull(message = "나이를 입력해주세요.")
    private Integer age;

    @Schema(description = "부원 전화번호", example = "010-1111-2222")
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNum;
}
