package org.one.domain.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adminId;

	@Column(nullable = false, unique = true, length = 50)
	private String username;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(insertable = false, updatable = false)
	private LocalDateTime createdAt;

	protected Admin() {}

	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Long getAdminId() { return adminId; }
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	public LocalDateTime getCreatedAt() { return createdAt; }
}
