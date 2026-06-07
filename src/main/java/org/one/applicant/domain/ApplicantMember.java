package org.one.applicant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.one.applicant.enums.Gender;
import org.one.global.entity.BaseEntity;

/**
 * 동아리 가입 신청자의 지원 정보와 최초 열람 상태를 저장하는 엔티티입니다.
 */
@Entity
@Table(name = "applicant_member")
@Builder
@AllArgsConstructor
public class ApplicantMember extends BaseEntity {

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

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected ApplicantMember() {}

	/**
	 * 가입 신청 정보를 저장할 엔티티를 생성합니다.
	 *
	 * @param name 신청자 이름
	 * @param department 신청자 학과
	 * @param studentId 신청자 학번
	 * @param birthday 신청자 생년월일
	 * @param grade 신청자 학년
	 * @param phoneNumber 신청자 연락처
	 * @param gender 신청자 성별
	 * @param motivation 지원 동기
	 * @param techStack 기술 스택
	 * @param desiredActivity 희망 활동
	 * @param finalWords 마지막으로 하고 싶은 말
	 * @param privacyConsent 개인정보 수집 동의 여부
	 */
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

	/**
	 * 신청자 ID를 반환합니다.
	 *
	 * @return 신청자 ID
	 */
	public Long getApplicantId() { return applicantId; }

	/**
	 * 신청자 이름을 반환합니다.
	 *
	 * @return 신청자 이름
	 */
	public String getName() { return name; }

	/**
	 * 신청자 학과를 반환합니다.
	 *
	 * @return 신청자 학과
	 */
	public String getDepartment() { return department; }

	/**
	 * 신청자 학번을 반환합니다.
	 *
	 * @return 신청자 학번
	 */
	public String getStudentId() { return studentId; }

	/**
	 * 신청자 생년월일을 반환합니다.
	 *
	 * @return 생년월일
	 */
	public LocalDate getBirthday() { return birthday; }

	/**
	 * 신청자 학년을 반환합니다.
	 *
	 * @return 학년
	 */
	public Integer getGrade() { return grade; }

	/**
	 * 신청자 연락처를 반환합니다.
	 *
	 * @return 연락처
	 */
	public String getPhoneNumber() { return phoneNumber; }

	/**
	 * 신청자 성별을 반환합니다.
	 *
	 * @return 성별
	 */
	public Gender getGender() { return gender; }

	/**
	 * 지원 동기를 반환합니다.
	 *
	 * @return 지원 동기
	 */
	public String getMotivation() { return motivation; }

	/**
	 * 기술 스택을 반환합니다.
	 *
	 * @return 기술 스택
	 */
	public String getTechStack() { return techStack; }

	/**
	 * 희망 활동을 반환합니다.
	 *
	 * @return 희망 활동
	 */
	public String getDesiredActivity() { return desiredActivity; }

	/**
	 * 마지막으로 하고 싶은 말을 반환합니다.
	 *
	 * @return 마지막 말
	 */
	public String getFinalWords() { return finalWords; }

	/**
	 * 개인정보 수집 동의 여부를 반환합니다.
	 *
	 * @return 동의 여부
	 */
	public Boolean getPrivacyConsent() { return privacyConsent; }

	/**
	 * 최초 열람 여부를 반환합니다.
	 *
	 * @return 최초 열람이면 true
	 */
	public Boolean getIsFirstView() { return isFirstView; }

	/**
	 * 신청서가 열람되었음을 표시합니다.
	 */
	public void markAsViewed() { this.isFirstView = false; }
}
