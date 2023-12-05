package com.ssafy.comment.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "CommentDto : 댓글 정보", description="상세보기한 게시글의 댓글 정보를 나타낸다.")
public class CommentInputDto {
	
	@ApiModelProperty(value = "댓글번호")
	private int commentId;
	@ApiModelProperty(value = "댓글 작성자")
	private String userId;
	@ApiModelProperty(value = "댓글 내용 ")
	private String comment;
	@ApiModelProperty(value = "게시글 번호")
	private int articleNo;
	
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
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	
	@Override
	public String toString() {
		return "CommentInputDto [commentId=" + commentId + ", userId=" + userId + ", comment=" + comment
				+ ", articleNo=" + articleNo + "]";
	}
	
}
