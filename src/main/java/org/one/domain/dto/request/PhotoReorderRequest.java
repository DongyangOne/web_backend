package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 사진 순서 일괄 변경 요청 DTO입니다.
 *
 * @param orders 사진 ID와 변경할 순서 목록
 */
@Schema(description = "사진 순서 일괄 변경 요청")
public record PhotoReorderRequest(
		@Schema(description = "사진 순서 목록")
		@NotEmpty(message = "순서 변경할 사진 목록을 입력해주세요.")
		@Valid
		List<PhotoOrderItem> orders
) {}
