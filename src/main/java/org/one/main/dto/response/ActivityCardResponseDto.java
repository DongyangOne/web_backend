package org.one.main.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.main.domain.ActivityCard;

/**
 * 주요활동 카드 응답 DTO입니다.
 */
@Schema(description = "주요활동 카드 응답")
@Getter
@Builder
@AllArgsConstructor
public class ActivityCardResponseDto {

    @Schema(description = "카드 ID", example = "5")
    private Long cardId;

    @Schema(description = "카드 제목", example = "팀 프로젝트")
    private String title;

    @Schema(description = "카드 내용", example = "매 학기 팀을 구성하여 실제 서비스 수준의 프로젝트를 기획·개발합니다. 기획부터 배포까지 전 과정을 경험할 수 있습니다.")
    private String content;

    @Schema(description = "카드 노출 순서", example = "1")
    private int cardOrder;

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
