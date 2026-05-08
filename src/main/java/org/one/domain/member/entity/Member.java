package org.one.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(nullable = false, length = 20)
	private String status;

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

	@Column(nullable = false, length = 10)
	private String gender;

	@Column(insertable = false, updatable = false)
	private LocalDateTime registeredAt;

	@Column
	private LocalDateTime lastPromotionAt;

	protected Member() {}

	public Member(String status, String name, String department, String studentId,
			LocalDate birthday, Integer grade, Integer age, String phoneNumber, String gender) {
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
	public String getStatus() { return status; }
	public String getName() { return name; }
	public String getDepartment() { return department; }
	public String getStudentId() { return studentId; }
	public LocalDate getBirthday() { return birthday; }
	public Integer getGrade() { return grade; }
	public Integer getAge() { return age; }
	public String getPhoneNumber() { return phoneNumber; }
	public String getGender() { return gender; }
	public LocalDateTime getRegisteredAt() { return registeredAt; }
	public LocalDateTime getLastPromotionAt() { return lastPromotionAt; }

	public void updateStatus(String status) { this.status = status; }
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
