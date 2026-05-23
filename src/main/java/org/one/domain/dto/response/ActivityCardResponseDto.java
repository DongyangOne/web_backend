package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.domain.entity.ActivityCard;

/**
 * 주요활동 카드 응답 DTO입니다.
 */
@Schema(description = "주요활동 카드 응답")
@Getter
@Builder
@AllArgsConstructor
public class ActivityCardResponseDto {

	@Schema(description = "카드 ID", example = "1")
	private Long cardId;

	@Schema(description = "카드 제목", example = "웹 개발 스터디")
	private String title;

	@Schema(description = "카드 내용", example = "매주 토요일 웹 개발 스터디를 진행합니다.")
	private String content;

	@Schema(description = "카드 순서", example = "1")
	private Integer cardOrder;

	/**
	 * ActivityCard 엔티티에서 응답 DTO를 생성합니다.
	 *
	 * @param card 주요활동 카드 엔티티
	 * @return 주요활동 카드 응답 DTO
	 */
	public static ActivityCardResponseDto from(ActivityCard card) {
		return ActivityCardResponseDto.builder()
				.cardId(card.getCardId())
				.title(card.getTitle())
				.content(card.getContent())
				.cardOrder(card.getCardOrder())
				.build();
	}
}
