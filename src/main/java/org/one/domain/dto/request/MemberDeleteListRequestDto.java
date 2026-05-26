package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Schema(description = "부원 삭제 요청")
@Getter
@Setter
public class MemberDeleteListRequestDto {
    @Schema(description = "삭제할 부원(들)의 id리스트")
    @NotEmpty(message = "id리스트를 입력해주세요.")
    private List<Long> memberIds;
}
