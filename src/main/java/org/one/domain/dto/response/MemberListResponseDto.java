package org.one.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import org.one.domain.entity.Member;
import org.one.domain.enums.MemberStatus;

@JsonPropertyOrder({"memberId", "name", "age", "studentId", "grade", "phoneNumber", "status"})
@Schema(description = "명부 리스트 조회 응답")
@Getter
public class MemberListResponseDto {
    private Long memberId;
    private String name;
    private String studentId;
    private Integer grade;
    private Integer age;
    private String phoneNumber;
    private MemberStatus status;

    public MemberListResponseDto(Member member){
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.grade = member.getGrade();
        this.age = member.getAge();
        this.phoneNumber = member.getPhoneNumber();
        this.status = member.getStatus();
    }
}
