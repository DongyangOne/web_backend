package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.domain.entity.ApplicantMember;

import java.time.LocalDateTime;

/**
 *  신청 부원 리스트 조회 시 리스트의 요소가 될 데이터 구조
 */
@Schema(description = "신청 부원 리스트 요소 데이터 구조")
@Getter
public class ApplicantMemberListResponseDto {
    private Long applicantId;
    private String name;
    private String studentId;
    private String phoneNum;
    private LocalDateTime createdAt;
    private Boolean isFirstView;

    public ApplicantMemberListResponseDto(ApplicantMember applicantMember){
        this.applicantId = applicantMember.getApplicantId();
        this.name = applicantMember.getName();
        this.studentId = applicantMember.getStudentId();
        this.phoneNum = applicantMember.getPhoneNumber();
        this.createdAt = applicantMember.getCreatedAt();
        this.isFirstView = applicantMember.getIsFirstView();
    }
}
