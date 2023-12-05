package com.ssafy.comment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CommentDto : 댓글 정보", description="상세보기한 게시글의 댓글 정보를 나타낸다.")
public class CommentDto {
	
	@ApiModelProperty(value = "댓글번호")
	private int commentId;
	@ApiModelProperty(value = "댓글 작성자")
	private String userId;
	@ApiModelProperty(value = "댓글 내용 ")
	private String comment;
	@ApiModelProperty(value = "댓글 작성시간")
	private String registerTime;
	@ApiModelProperty(value = "게시글 번호")
	private int articleNo;
	@ApiModelProperty(value = "닉네임")
	private String userNickname;
	
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getUserNickname() {
		return userNickname;
	}
	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}
	
	@Override
	public String toString() {
		return "CommentDto [commentId=" + commentId + ", userId=" + userId + ", comment=" + comment + 
				", registerTime=" + registerTime + ", articleNo=" + articleNo + ", userNickname="
				+ userNickname + "]";
	}
	
	
	
	
	
}
