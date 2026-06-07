package org.one.member.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.member.domain.ApplicantMember;
import org.one.member.enums.Gender;

import java.time.LocalDate;


@JsonPropertyOrder({"applicantId", "name", "studentId", "department", "grade", "gender", "phoneNumber", "birthday", "techStack", "desiredActivity", "motivation", "finalWords"})
@Schema(description = "신청 부원 상세 정보 응답")
@Getter
public class ApplicantMemberDetailResponseDto {
    @Schema(description = "신청 부원 id", example = "3")
    private Long applicantId;

    @Schema(description = "신청 부원 이름", example = "홍길동")
    private String name;

    @Schema(description = "신청 부원 학과", example = "웹응용소프트웨어공학과")
    private String department;

    @Schema(description = "신청 부원 학번", example = "20991234")
    private String studentId;

    @Schema(description = "신청 부원 생년월일", example = "2001-01-01")
    private LocalDate birthday;

    @Schema(description = "신청 부원 학년", example = "3")
    private Integer grade;

    @Schema(description = "신청 부원 전화번호", example = "010-1111-2222")
    private String phoneNumber;

    @Schema(description = "신청 부원 성별", example = "FEMALE")
    private Gender gender;

    @Schema(description = "신청 부원 지원 동기", example = "웹 개발에 관심이 많아 실무적인 프로젝트 경험을 쌓고 싶어 지원했습니다.")
    private String motivation;

    @Schema(description = "신청 부원 기술 스택", example = "Java, Spring")
    private String techStack;

    @Schema(description = "신청 부원 희망활동", example = "프론트엔드 UI/UX 개발")
    private String desiredActivity;

    @Schema(description = "신청 부원 마지막으로 하고 싶은 말", example = "잘 부탁드립니다.")
    private String finalWords;

    private ApplicantMemberDetailResponseDto(ApplicantMember applicantMember){
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

    public static ApplicantMemberDetailResponseDto from(ApplicantMember applicantMember){
        return new ApplicantMemberDetailResponseDto(applicantMember);
    }
}
