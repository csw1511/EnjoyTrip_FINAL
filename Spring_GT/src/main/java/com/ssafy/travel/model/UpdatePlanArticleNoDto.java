package com.ssafy.travel.model;

public class UpdatePlanArticleNoDto {
	private String articleNo;
	private String planId;
	public String getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	@Override
	public String toString() {
		return "UpdatePlanArticleNo [articleNo=" + articleNo + ", planId=" + planId + "]";
	}
	

}
