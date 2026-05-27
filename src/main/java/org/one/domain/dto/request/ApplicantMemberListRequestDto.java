package org.one.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.one.global.pagination.RequestPagingDto;


@Schema(description = "신청 부원 리스트 조회 요청 dto")
@Getter
@Setter
public class ApplicantMemberListRequestDto extends RequestPagingDto {
    //생성자를 통해 기본값설정(한번에 가져올 개수, 오래된 순)
    public ApplicantMemberListRequestDto(){
        this.setSize(100);
        this.setSort("createdAt");
        this.setDirection("ASC");
    }
}
