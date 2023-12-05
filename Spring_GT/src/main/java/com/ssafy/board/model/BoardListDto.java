package com.ssafy.board.model;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "BoardListDto : 게시글 목록과 페이지 정보", description = "게시글 목록과 현재 페이지, 전체 페이지 정보를 나타낸다.")
public class BoardListDto {

	@ApiModelProperty(value = "글번호")
	private int articleNo;
	@ApiModelProperty(value = "글목록")
	private List<BoardDto> articles;
	@ApiModelProperty(value = "현재 페이지번호")
	private int currentPage;
	@ApiModelProperty(value = "전체 페이지 수")
	private int totalPageCount;
	
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public List<BoardDto> getArticles() {
		return articles;
	}
	public void setArticles(List<BoardDto> articles) {
		this.articles = articles;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	
	@Override
	public String toString() {
		return "BoardListDto [articles=" + articles + ", currentPage=" + currentPage + ", totalPageCount="
				+ totalPageCount + "]";
	}
}
