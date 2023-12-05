package com.ssafy.hotplace.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "HotplaceDto : 핫플레이스 정보", description="핫플레이스의 상세 정보를 나타낸다.")
public class HotplaceDto {
	
	@ApiModelProperty(value = "핫플레이스 글 번호")
	private String articleNo;
	@ApiModelProperty(value = "핫플레이스 장소 타이틀")
	private String title;
	@ApiModelProperty(value = "핫플레이스 등록자")
	private String userId;
	@ApiModelProperty(value = "등록자 아이디")
	private String userNickname;
	@ApiModelProperty(value = "핫플레이스 아이디")
	private int contentId;
	@ApiModelProperty(value = "핫플레이스 사진")
	private String firstImage;
	@ApiModelProperty(value = "핫플레이스 사진2")
	private String firstImage2;
	@ApiModelProperty(value = "핫플레이스 조회수")
	private int hit;
	@ApiModelProperty(value = "핫플레이스 글 등록 날짜")
	private String registTime;
	@ApiModelProperty(value = "핫플레이스 설명")
	private String overview;
	@ApiModelProperty(value = "핫플레이스 주소1")
	private String addr1;
	@ApiModelProperty(value = "핫플레이스 주소2")
	private String addr2;
	@ApiModelProperty(value = "메모")
	private String memo;
	@ApiModelProperty(value = "좋아요 수")
	private int favoriteCount;

	private String latitude;
	private String longitude;
	
	public String getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getContentId() {
		return contentId;
	}
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public String getFirstImage2() {
		return firstImage2;
	}
	public void setFirstImage2(String firstImage2) {
		this.firstImage2 = firstImage2;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegistTime() {
		return registTime;
	}
	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	@Override
	public String toString() {
		return "HotplaceDto [articleNo=" + articleNo + ", title=" + title + ", userId=" + userId + ", userNickname="
				+ userNickname + ", contentId=" + contentId + ", firstImage=" + firstImage + ", firstImage2="
				+ firstImage2 + ", hit=" + hit + ", registTime=" + registTime + ", overview=" + overview + ", addr1="
				+ addr1 + ", addr2=" + addr2 + ", memo=" + memo + ", favoriteCount=" + favoriteCount + ", latitude="
				+ latitude + ", longitude=" + longitude + "]";
	}
	
	
}
