package com.ssafy.qna.model.service;

import java.util.Map;

import com.ssafy.qna.model.QnaDto;
import com.ssafy.qna.model.QnaListDto;

public interface QnaService {
	
	void writeArticle(QnaDto qnaDto) throws Exception;

	QnaListDto listArticle(Map<String, String> map) throws Exception;

//	int getTotalArticleCount(Map<String, Object> map) throws Exception;

	QnaDto getArticle(int articleNo) throws Exception;

	void updateHit(int articleNo) throws Exception;

	void modifyArticle(QnaDto qnaDto) throws Exception;

	void deleteArticle(int articleNo) throws Exception;

}
