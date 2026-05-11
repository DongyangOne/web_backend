package org.one.domain.member.entity;

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
import org.one.global.enums.Gender;

@Entity
@Table(name = "member")
public class Member {

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

	@Column(insertable = false, updatable = false)
	private LocalDateTime registeredAt;

	@Column
	private LocalDateTime lastPromotionAt;

	protected Member() {}

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

	public Long getMemberId() { return memberId; }
	public MemberStatus getStatus() { return status; }
	public String getName() { return name; }
	public String getDepartment() { return department; }
	public String getStudentId() { return studentId; }
	public LocalDate getBirthday() { return birthday; }
	public Integer getGrade() { return grade; }
	public Integer getAge() { return age; }
	public String getPhoneNumber() { return phoneNumber; }
	public Gender getGender() { return gender; }
	public LocalDateTime getRegisteredAt() { return registeredAt; }
	public LocalDateTime getLastPromotionAt() { return lastPromotionAt; }

	public void updateStatus(MemberStatus status) { this.status = status; }
	public void updateInfo(String name, String department, String phoneNumber,
			Integer grade, Integer age) {
		this.name = name;
		this.department = department;
		this.phoneNumber = phoneNumber;
		this.grade = grade;
		this.age = age;
	}

	public void promoteByYears(int years, LocalDateTime promotedAt) {
		if (years <= 0) return;
		this.grade = this.grade + years;
		this.lastPromotionAt = promotedAt;
	}
}
