package org.one.domain.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.one.global.pagination.RequestPagingDto;

@Getter
@Setter
public class MemberListRequestDto extends RequestPagingDto {
    public MemberListRequestDto(){
        this.setSize(1000);
        this.setSort("createdAt");
        this.setDirection("ASC");
    }
}
