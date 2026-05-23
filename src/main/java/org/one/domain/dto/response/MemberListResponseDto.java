package org.one.domain.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.one.domain.entity.Member;
import org.one.domain.enums.MemberStatus;

@Getter
public class MemberListResponseDto {
    private String name;
    private String studentId;
    private Integer grade;
    private Integer age;
    private String phoneNumber;
    private MemberStatus status;

    public MemberListResponseDto(Member member){
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.grade = member.getGrade();
        this.age = member.getAge();
        this.phoneNumber = member.getPhoneNumber();
        this.status = member.getStatus();
    }
}
