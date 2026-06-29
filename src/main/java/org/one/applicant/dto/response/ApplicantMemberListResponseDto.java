package org.one.applicant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.applicant.domain.ApplicantMember;

import java.time.LocalDateTime;

/**
 * 신청 부원 리스트 조회 시 리스트의 요소가 될 데이터 구조
 */
@Schema(description = "신청 부원 리스트 요소 데이터 구조")
@Getter
public class ApplicantMemberListResponseDto {
    @Schema(description = "신청 부원 id", example = "1")
    private Long applicantId;
    @Schema(description = "신청 부원 이름", example = "홍길동")
    private String name;
    @Schema(description = "신청 부원 학번", example = "20240001")
    private String studentId;
    @Schema(description = "신청 부원 전화번호", example = "010-1111-2222")
    private String phoneNum;
    @Schema(description = "신청 부원 신청 날짜", example = "2026-05-20")
    private LocalDateTime createdAt;
    @Schema(description = "신청 부원 정보 조회 여부", example = "false")
    private Boolean isFirstView;

    private ApplicantMemberListResponseDto(ApplicantMember applicantMember) {
        this.applicantId = applicantMember.getApplicantId();
        this.name = applicantMember.getName();
        this.studentId = applicantMember.getStudentId();
        this.phoneNum = applicantMember.getPhoneNumber();
        this.createdAt = applicantMember.getCreatedAt();
        this.isFirstView = applicantMember.getIsFirstView();
    }

    public static ApplicantMemberListResponseDto from(ApplicantMember applicantMember) {
        return new ApplicantMemberListResponseDto(applicantMember);
    }
}
