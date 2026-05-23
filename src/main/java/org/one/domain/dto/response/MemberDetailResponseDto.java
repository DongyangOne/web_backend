package org.one.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.one.domain.entity.Member;

/**
 * 멤버 상세 조회 성공 시 응답하는 dto
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

    //생성자를 통해 Member엔티티에서 필요한 정보만 dto 필드로 저장
    public MemberDetailResponseDto(Member member){
        this.name = member.getName();
        this.grade = member.getGrade();
        this.studentId = member.getStudentId();
        this.age = member.getAge();
        this.phoneNum = member.getPhoneNumber();
    }
}
