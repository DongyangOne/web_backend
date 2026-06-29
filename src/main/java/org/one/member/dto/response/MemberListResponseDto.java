package org.one.member.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.member.domain.Member;
import org.one.member.enums.MemberStatus;


@JsonPropertyOrder({"memberId", "name", "age", "studentId", "grade", "phoneNumber", "status"})
@Schema(description = "명부 리스트 조회 응답")
@Getter
public class MemberListResponseDto {
    @Schema(description = "부원 id", example = "13")
    private Long memberId;
    @Schema(description = "부원 이름", example = "김철수")
    private String name;
    @Schema(description = "부원 학번", example = "20260004")
    private String studentId;
    @Schema(description = "부원 학년", example = "4")
    private Integer grade;
    @Schema(description = "부원 나이", example = "23")
    private Integer age;
    @Schema(description = "부원 전화번호", example = "010-4444-4444")
    private String phoneNumber;
    @Schema(description = "부원 활동 상태", example = "ACTIVE")
    private MemberStatus status;

    private MemberListResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.grade = member.getGrade();
        this.age = member.getAge();
        this.phoneNumber = member.getPhoneNumber();
        this.status = member.getStatus();
    }

    public static MemberListResponseDto from(Member member) {
        return new MemberListResponseDto(member);
    }
}
