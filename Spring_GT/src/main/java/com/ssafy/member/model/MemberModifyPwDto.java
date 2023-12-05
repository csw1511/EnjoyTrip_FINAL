package com.ssafy.member.model;

public class MemberModifyPwDto {
	private String userId;
	private String userPasswordOld;
	private String userPasswordNew;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPasswordOld() {
		return userPasswordOld;
	}
	public void setUserPasswordOld(String userPasswordOld) {
		this.userPasswordOld = userPasswordOld;
	}
	public String getUserPasswordNew() {
		return userPasswordNew;
	}
	public void setUserPasswordNew(String userPasswordNew) {
		this.userPasswordNew = userPasswordNew;
	}
	@Override
	public String toString() {
		return "MemberModifyPwDto [userId=" + userId + ", userPasswordOld=" + userPasswordOld + ", userPasswordNew="
				+ userPasswordNew + "]";
	}
	
}
