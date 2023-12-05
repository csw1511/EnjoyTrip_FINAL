package com.ssafy.travel.model;

public class PlanDetailDto {
	private int planId;
	private int planOrder;
	private String contentId;
	private String memo;
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public int getPlanOrder() {
		return planOrder;
	}
	public void setPlanOrder(int planOrder) {
		this.planOrder = planOrder;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return "PlanDetailDto [planId=" + planId + ", planOrder=" + planOrder + ", contentId=" + contentId + ", memo="
				+ memo + "]";
	}
	
	
}
