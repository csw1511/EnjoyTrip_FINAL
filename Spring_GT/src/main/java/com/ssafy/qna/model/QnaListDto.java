package com.ssafy.qna.model;

import java.util.List;

public class QnaListDto {
	
	private List<QnaDto> articles;
	private int currentPage;
	private int totalPageCount;
	
	public List<QnaDto> getArticles() {
		return articles;
	}
	public void setArticles(List<QnaDto> articles) {
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
	
}
