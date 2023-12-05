package com.ssafy.travel.model;

public class FindByContentIdDto extends AttractionDto {
	private int planId;
	private int planOrder;
	private String memoEachAttr;
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
	public String getMemoEachAttr() {
		return memoEachAttr;
	}
	public void setMemoEachAttr(String memoEachAttr) {
		this.memoEachAttr = memoEachAttr;
	}
	@Override
	public String toString() {
		return "FindByContentIdDto [planId=" + planId + ", planOrder=" + planOrder + ", memoEachAttr=" + memoEachAttr
				+ ", getContentId()=" + getContentId() + ", getContentTypeId()=" + getContentTypeId() + ", getTitle()="
				+ getTitle() + ", getAddr1()=" + getAddr1() + ", getOverview()=" + getOverview() + ", getZipcode()="
				+ getZipcode() + ", getFirstImage()=" + getFirstImage() + ", getFirstImage2()=" + getFirstImage2()
				+ ", getSidoCode()=" + getSidoCode() + ", getGugunCode()=" + getGugunCode() + ", getLatitude()="
				+ getLatitude() + ", getLongitude()=" + getLongitude() + ", getMemo()=" + getMemo() + ", isUsed()="
				+ isUsed() + ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + "]";
	}
	
}
