package org.one.global.pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "페이징 공통 응답 데이터")
@Getter
@Builder
public class ResponsePagingDto<T> {

    @Schema(description = "실제 데이터 리스트 (T 타입에 따라 동적 변경)")
    private List<T> content;

    @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
    private Integer page;

    @Schema(description = "한 페이지당 요청한 데이터 개수", example = "15")
    private Integer size;

    @Schema(description = "전체 데이터 개수", example = "45")
    private Long totalElements;

    @Schema(description = "전체 페이지 수", example = "3")
    private Integer totalPages;

    @Schema(description = "마지막 페이지 여부", example = "false")
    private Boolean last;

    public static <T> ResponsePagingDto<T> from(Page<T> page) {
        return ResponsePagingDto.<T>builder()
                .content(page.getContent())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}