package org.one.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.one.global.entity.BaseEntity;

/**
 * 관리자 로그인 계정을 표현하는 엔티티입니다.
 */
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminId;

	@Column(nullable = false, unique = true, length = 50)
	private String username;

	@Column(nullable = false, length = 255)
	private String password;

	/**
	 * JPA 엔티티 생성을 위한 기본 생성자입니다.
	 */
	protected Admin() {}

	/**
	 * 관리자 계정 엔티티를 생성합니다.
	 *
	 * @param username 관리자 아이디
	 * @param password 암호화된 관리자 비밀번호
	 */
	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * 관리자 ID를 반환합니다.
	 *
	 * @return 관리자 ID
	 */
	public Long getAdminId() { return adminId; }

	/**
	 * 관리자 아이디를 반환합니다.
	 *
	 * @return 관리자 아이디
	 */
	public String getUsername() { return username; }

	/**
	 * 암호화된 관리자 비밀번호를 반환합니다.
	 *
	 * @return 암호화된 비밀번호
	 */
	public String getPassword() { return password; }

}
