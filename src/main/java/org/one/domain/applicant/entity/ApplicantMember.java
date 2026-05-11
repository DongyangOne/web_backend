package org.one.domain.applicant.entity;

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
@Table(name = "applicant_member")
public class ApplicantMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long applicantId;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 100)
	private String department;

	@Column(nullable = false, length = 8)
	private String studentId;

	@Column(nullable = false)
	private LocalDate birthday;

	@Column(nullable = false)
	private Integer grade;

	@Column(nullable = false, length = 20)
	private String phoneNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 10)
	private Gender gender;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String motivation;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String techStack;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String desiredActivity;

	@Column(columnDefinition = "TEXT")
	private String finalWords;

	@Column(nullable = false)
	private Boolean privacyConsent;

	@Column(nullable = false)
	private Boolean isFirstView;

	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	protected ApplicantMember() {}

	public ApplicantMember(String name, String department, String studentId, LocalDate birthday,
			Integer grade, String phoneNumber, Gender gender, String motivation,
			String techStack, String desiredActivity, String finalWords, Boolean privacyConsent) {
		this.name = name;
		this.department = department;
		this.studentId = studentId;
		this.birthday = birthday;
		this.grade = grade;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.motivation = motivation;
		this.techStack = techStack;
		this.desiredActivity = desiredActivity;
		this.finalWords = finalWords;
		this.privacyConsent = privacyConsent;
		this.isFirstView = true;
	}

	public Long getApplicantId() { return applicantId; }
	public String getName() { return name; }
	public String getDepartment() { return department; }
	public String getStudentId() { return studentId; }
	public LocalDate getBirthday() { return birthday; }
	public Integer getGrade() { return grade; }
	public String getPhoneNumber() { return phoneNumber; }
	public Gender getGender() { return gender; }
	public String getMotivation() { return motivation; }
	public String getTechStack() { return techStack; }
	public String getDesiredActivity() { return desiredActivity; }
	public String getFinalWords() { return finalWords; }
	public Boolean getPrivacyConsent() { return privacyConsent; }
	public Boolean getIsFirstView() { return isFirstView; }
	public LocalDateTime getCreatedAt() { return createdAt; }

	public void markAsViewed() { this.isFirstView = false; }
}
