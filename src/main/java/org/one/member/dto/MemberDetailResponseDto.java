package org.one.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.one.member.domain.Member;


/**
 * 특정 부원 정보 조회 성공 시 응답하는 dto
 */
@Schema(description = "부원 상세 정보 응답")
@Getter
public class MemberDetailResponseDto {
    @Schema(description = "부원 이름")
    private String name;

    @Schema(description = "부원 학년")
    private Integer grade;

    @Schema(description = "부원 학번")
    private String studentId;

    @Schema(description = "부원 나이")
    private Integer age;

    @Schema(description = "부원 전화번호")
    private String phoneNum;

    private MemberDetailResponseDto(Member member){
        this.name = member.getName();
        this.grade = member.getGrade();
        this.studentId = member.getStudentId();
        this.age = member.getAge();
        this.phoneNum = member.getPhoneNumber();
    }

    public static MemberDetailResponseDto from(Member member){
        return new MemberDetailResponseDto(member);
    }
}
