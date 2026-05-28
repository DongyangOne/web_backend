package org.one.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.domain.entity.ApplicantMember;

import java.time.LocalDate;

@JsonPropertyOrder({"applicantId", "name","age", "studentId", "grade", "phoneNumber"})
@Schema(description = "신청 정보 불러오기 응답")
@Getter
public class ApplicantInfoResponseDto {
    @Schema(description = "신청 부원 id")
    private Long applicantId;

    @Schema(description = "신청 부원 이름")
    private String name;

    @Schema(description = "신청 부원 학번")
    private String studentId;

    @Schema(description = "신청 부원 나이")
    private Integer age;

    @Schema(description = "신청 부원 학년")
    private Integer grade;

    @Schema(description = "신청 부원 전화번호")
    private String phoneNumber;

    public ApplicantInfoResponseDto(ApplicantMember applicantMember){
        this.applicantId = applicantMember.getApplicantId();
        this.name = applicantMember.getName();
        this.age = LocalDate.now().getYear() - applicantMember.getBirthday().getYear()+1; //한국식 나이 적용
        this.studentId = applicantMember.getStudentId();
        this.grade = applicantMember.getGrade();
        this.phoneNumber = applicantMember.getPhoneNumber();
    }
}
