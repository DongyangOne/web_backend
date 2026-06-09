package org.one.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(description = "부원 일괄 삭제 요청")
@Getter
@NoArgsConstructor
public class MemberDeleteRequestDto {
    @Schema(description = "삭제할 부원들의 ID 리스트 (쿼리 파라미터)", type = "array", example = "[1, 2, 7]")
    @NotEmpty(message = "삭제할 부원(들)의 id리스트를 입력해주세요.")
    private List<@NotNull(message = "id는 비어있을 수 없습니다.") @Positive(message = "올바르지 않은 부원의 id가 포함되어있습니다.") Long> memberIds;
}