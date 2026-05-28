package org.one.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.one.domain.entity.ApplicantMember;
import org.one.domain.enums.Gender;

import java.time.LocalDate;


@JsonPropertyOrder({"applicantId", "name", "studentId", "department", "grade", "gender", "phoneNumber", "birthday", "techStack", "desiredActivity", "motivation", "finalWords"})
@Schema(description = "신청 부원 상세 정보 응답")
@Getter
public class ApplicantMemberDetailResponseDto {
    @Schema(description = "신청 부원 id")
    private Long applicantId;

    @Schema(description = "신청 부원 이름")
    private String name;

    @Schema(description = "신청 부원 학과")
    private String department;

    @Schema(description = "신청 부원 학번")
    private String studentId;

    @Schema(description = "신청 부원 생년월일")
    private LocalDate birthday;

    @Schema(description = "신청 부원 학년")
    private Integer grade;

    @Schema(description = "신청 부원 전화번호")
    private String phoneNumber;

    @Schema(description = "신청 부원 성별")
    private Gender gender;

    @Schema(description = "신청 부원 지원 동기")
    private String motivation;

    @Schema(description = "신청 부원 기술 스택")
    private String techStack;

    @Schema(description = "신청 부원 희망활동")
    private String desiredActivity;

    @Schema(description = "신청 부원 마지막으로 하고 싶은 말")
    private String finalWords;

    public ApplicantMemberDetailResponseDto(ApplicantMember applicantMember){
        this.applicantId = applicantMember.getApplicantId();
        this.name = applicantMember.getName();
        this.birthday = applicantMember.getBirthday();
        this.department = applicantMember.getDepartment();
        this.studentId = applicantMember.getStudentId();
        this.motivation = applicantMember.getMotivation();
        this.grade = applicantMember.getGrade();
        this.phoneNumber = applicantMember.getPhoneNumber();
        this.gender = applicantMember.getGender();
        this.techStack = applicantMember.getTechStack();
        this.desiredActivity = applicantMember.getDesiredActivity();
        this.finalWords = applicantMember.getFinalWords();
    }
}
