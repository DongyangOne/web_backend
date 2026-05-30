package org.one.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.global.pagination.RequestPagingDto;

@Schema(description = "명부 리스트 조회 요청(페이지 설정)")
@Getter
public class MemberListRequestDto extends RequestPagingDto {
    public MemberListRequestDto(){
        this.setSize(15);
        this.setSort("createdAt");
        this.setDirection("ASC");
    }
}
