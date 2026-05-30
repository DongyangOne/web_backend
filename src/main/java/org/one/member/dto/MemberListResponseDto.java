package org.one.member.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.member.domain.Member;
import org.one.member.enums.MemberStatus;


@JsonPropertyOrder({"memberId", "name", "age", "studentId", "grade", "phoneNumber", "status"})
@Schema(description = "명부 리스트 조회 응답")
@Getter
public class MemberListResponseDto {
    @Schema(description = "부원 id")
    private Long memberId;
    @Schema(description = "부원 이름")
    private String name;
    @Schema(description = "부원 학번")
    private String studentId;
    @Schema(description = "부원 학년")
    private Integer grade;
    @Schema(description = "부원 나이")
    private Integer age;
    @Schema(description = "부원 전화번호")
    private String phoneNumber;
    @Schema(description = "부원 활동 상태")
    private MemberStatus status;

    private MemberListResponseDto(Member member){
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.grade = member.getGrade();
        this.age = member.getAge();
        this.phoneNumber = member.getPhoneNumber();
        this.status = member.getStatus();
    }

    public static MemberListResponseDto from(Member member){
        return new MemberListResponseDto(member);
    }
}
