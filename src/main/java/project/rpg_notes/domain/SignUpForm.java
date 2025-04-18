package project.rpg_notes.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SignUpForm {
	@NotEmpty
	@Size(min=3, max=30)
	private String username="";
	
	@NotEmpty
	@Size(min=7, max=30)
	private String password="";
	
	@NotEmpty
	@Size(min=7, max=30)
	private String passwordCheck="";
	
	private String role = "USER";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "SignUpForm [username=" + username + ", password=" + password + ", passwordCheck=" + passwordCheck
				+ ", role=" + role + "]";
	}

}
