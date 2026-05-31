package org.one.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.one.global.pagination.RequestPagingDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Schema(description = "명부 리스트 조회 요청(페이지 설정)")
public class MemberListRequestDto extends RequestPagingDto {
    public MemberListRequestDto(){
        //기본값 세팅
        this.setPage(0);
        this.setSize(15);
        this.setSort("createdAt");
        this.setDirection("ASC");
    }

    @Override
    @Schema(description = "페이지 번호 (0부터 시작)", example = "0")
    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
    public Integer getPage() {
        return super.getPage();
    }

    @Override
    @Schema(description = "한 페이지에 보여줄 부원 수 [15 고정]", example = "15")
    @Min(value = 15, message = "명부 조회의 페이지 크기는 15로 고정되어 있습니다.")
    @Max(value = 15, message = "명부 조회의 페이지 크기는 15로 고정되어 있습니다.")
    public Integer getSize() {
        return super.getSize();
    }

    @Override
    @Schema(description = "정렬 기준 필드", example = "createdAt")
    @Pattern(regexp = "^(createdAt|grade)$", message = "정렬 기준은 createdAt 또는 grade만 가능합니다.")
    public String getSort() {
        return super.getSort();
    }

    @Override
    @Schema(description = "정렬 방향", example = "ASC")
    @Pattern(regexp = "^(ASC|DESC)$", message = "정렬 방향은 ASC 또는 DESC만 가능합니다.")
    public String getDirection() {
        return super.getDirection();
    }
}
