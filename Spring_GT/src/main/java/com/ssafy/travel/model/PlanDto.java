package com.ssafy.travel.model;

public class PlanDto {
	private int planId;
	private String title;
	private String content;
	private String userId;
	private String userNickname;
	private String startDate;
	private String endDate;
	private int articleNo;
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	@Override
	public String toString() {
		return "PlanDto [title=" + title + ", content=" + content + ", userId=" + userId + ", userNickname="
				+ userNickname + ", startDate=" + startDate + ", endDate=" + endDate + ", articleNo=" + articleNo + "]";
	}
	
}
