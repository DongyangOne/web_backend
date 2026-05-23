package org.one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.one.domain.enums.Gender;
import org.one.domain.enums.MemberStatus;
import org.one.global.entity.BaseEntity;

/**
 * 정규 부원의 기본 정보, 상태, 학년 승급 기준 시각을 저장하는 엔티티입니다.
 */
@Entity
@Table(name = "member")
@Builder
@AllArgsConstructor
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private MemberStatus status;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 100)
	private String department;

	@Column(nullable = false, unique = true, length = 8)
	private String studentId;

	@Column(nullable = false)
	private LocalDate birthday;

	@Column(nullable = false)
	private Integer grade;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false, length = 20)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Gender gender;

	@Column
	private LocalDateTime lastPromotionAt;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected Member() {}

	/**
	 * 정규 부원 엔티티를 생성합니다.
	 *
	 * @param status 부원 상태
	 * @param name 이름
	 * @param department 학과
	 * @param studentId 학번
	 * @param birthday 생년월일
	 * @param grade 학년
	 * @param age 나이
	 * @param phoneNumber 연락처
	 * @param gender 성별
	 */
	public Member(MemberStatus status, String name, String department, String studentId,
			LocalDate birthday, Integer grade, Integer age, String phoneNumber, Gender gender) {
		this.status = status;
		this.name = name;
		this.department = department;
		this.studentId = studentId;
		this.birthday = birthday;
		this.grade = grade;
		this.age = age;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
	}

	/**
	 * 부원 ID를 반환합니다.
	 *
	 * @return 부원 ID
	 */
	public Long getMemberId() { return memberId; }

	/**
	 * 부원 상태를 반환합니다.
	 *
	 * @return 부원 상태
	 */
	public MemberStatus getStatus() { return status; }

	/**
	 * 부원 이름을 반환합니다.
	 *
	 * @return 이름
	 */
	public String getName() { return name; }

	/**
	 * 부원 학과를 반환합니다.
	 *
	 * @return 학과
	 */
	public String getDepartment() { return department; }

	/**
	 * 부원 학번을 반환합니다.
	 *
	 * @return 학번
	 */
	public String getStudentId() { return studentId; }

	/**
	 * 부원 생년월일을 반환합니다.
	 *
	 * @return 생년월일
	 */
	public LocalDate getBirthday() { return birthday; }

	/**
	 * 부원 학년을 반환합니다.
	 *
	 * @return 학년
	 */
	public Integer getGrade() { return grade; }

	/**
	 * 부원 나이를 반환합니다.
	 *
	 * @return 나이
	 */
	public Integer getAge() { return age; }

	/**
	 * 부원 연락처를 반환합니다.
	 *
	 * @return 연락처
	 */
	public String getPhoneNumber() { return phoneNumber; }

	/**
	 * 부원 성별을 반환합니다.
	 *
	 * @return 성별
	 */
	public Gender getGender() { return gender; }

	/**
	 * 부원 등록 시각을 반환합니다.
	 *
	 * @return 등록 시각
	 */
	public LocalDateTime getRegisteredAt() { return getCreatedAt(); }

	/**
	 * 마지막 학년 승급 처리 시각을 반환합니다.
	 *
	 * @return 마지막 승급 시각
	 */
	public LocalDateTime getLastPromotionAt() { return lastPromotionAt; }

	/**
	 * 부원 상태를 변경합니다.
	 *
	 * @param status 새 부원 상태
	 */
	public void updateStatus(MemberStatus status) { this.status = status; }

	/**
	 * 부원 기본 정보를 수정합니다.
	 *
	 * @param name 이름
	 * @param department 학과
	 * @param phoneNumber 연락처
	 * @param grade 학년
	 * @param age 나이
	 */
	public void updateInfo(String name, String department, String phoneNumber,
			Integer grade, Integer age) {
		this.name = name;
		this.department = department;
		this.phoneNumber = phoneNumber;
		this.grade = grade;
		this.age = age;
	}

	/**
	 * 지정한 연수만큼 학년을 올리고 마지막 승급 시각을 기록합니다.
	 *
	 * @param years 승급할 연수
	 * @param promotedAt 승급 처리 시각
	 */
	public void promoteByYears(int years, LocalDateTime promotedAt) {
		if (years <= 0) {
			return;
		}
		this.grade = this.grade + years;
		this.lastPromotionAt = promotedAt;
	}
}
