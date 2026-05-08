package org.one.global.security.jwt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean revoked = false;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(insertable = false)
    private LocalDateTime updatedAt;

    protected RefreshToken() {}

    public RefreshToken(Long userId, String role, String token, LocalDateTime expiresAt) {
        this.userId = userId;
        this.role = role;
        this.token = token;
        this.expiresAt = expiresAt;
        this.revoked = false;
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getRole() { return role; }
    public String getToken() { return token; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isRevoked() { return revoked; }

    public void revoke() { this.revoked = true; }
    public void rotate(String newToken, LocalDateTime newExpiresAt) {
        this.token = newToken;
        this.expiresAt = newExpiresAt;
        this.revoked = false;
    }
}
