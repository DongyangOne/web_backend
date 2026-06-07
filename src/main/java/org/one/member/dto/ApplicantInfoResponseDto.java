package org.one.member.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.member.domain.ApplicantMember;

import java.time.LocalDate;

@JsonPropertyOrder({"applicantId", "name","age", "studentId", "grade", "phoneNumber"})
@Schema(description = "신청 정보 불러오기 응답")
@Getter
public class ApplicantInfoResponseDto {
    @Schema(description = "신청 부원 id", example = "3")
    private Long applicantId;

    @Schema(description = "신청 부원 이름", example = "홍길동")
    private String name;

    @Schema(description = "신청 부원 학번", example = "20991234")
    private String studentId;

    @Schema(description = "신청 부원 나이", example = "21")
    private Integer age;

    @Schema(description = "신청 부원 학년", example = "3")
    private Integer grade;

    @Schema(description = "신청 부원 전화번호", example = "010-1111-2222")
    private String phoneNumber;

    private ApplicantInfoResponseDto(ApplicantMember applicantMember){
        this.applicantId = applicantMember.getApplicantId();
        this.name = applicantMember.getName();
        this.age = LocalDate.now().getYear() - applicantMember.getBirthday().getYear()+1; //한국식 나이 적용
        this.studentId = applicantMember.getStudentId();
        this.grade = applicantMember.getGrade();
        this.phoneNumber = applicantMember.getPhoneNumber();
    }

    public static ApplicantInfoResponseDto from(ApplicantMember applicantMember){
        return new ApplicantInfoResponseDto(applicantMember);
    }
}
