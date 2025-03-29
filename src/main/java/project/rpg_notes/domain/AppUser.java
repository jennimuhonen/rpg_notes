package project.rpg_notes.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="application_user")
public class AppUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable=false, updatable=false)
	private Long AppUserId;
	
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	@Column(name="password", nullable=false)
	private String passwordHash;
	
	@Column(name="role", nullable=true)
	private String role;

	public AppUser() {
		
	}
	
	public AppUser(String username, String passwordHash, String role) {
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public AppUser(Long appUserId, String username, String passwordHash, String role) {
		AppUserId = appUserId;
		this.username = username;
		this.passwordHash = passwordHash;
		this.role = role;
	}

	public Long getAppUserId() {
		return AppUserId;
	}

	public void setAppUserId(Long appUserId) {
		AppUserId = appUserId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AppUser [AppUserId=" + AppUserId + ", username=" + username + ", passwordHash=" + passwordHash
				+ ", role=" + role + "]";
	}
	
}
