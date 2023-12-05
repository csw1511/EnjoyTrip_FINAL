package com.ssafy.member.model;

public class LoginDto {
	private String userId;
	private String userPassword;
	private String refreshToken;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	@Override
	public String toString() {
		return "LoginDto [userId=" + userId + ", userPassword=" + userPassword + ", refreshToken=" + refreshToken + "]";
	}
	
	
}
