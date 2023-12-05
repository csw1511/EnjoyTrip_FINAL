package com.ssafy.qna.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.qna.model.QnaDto;

@Mapper
public interface QnaMapper {
	
	void writeArticle(QnaDto qnaDto) throws SQLException;

	List<QnaDto> listArticle(Map<String, Object> param) throws SQLException;

	int getTotalArticleCount(Map<String, Object> param) throws SQLException;

	QnaDto getArticle(int articleNo) throws SQLException;

	void updateHit(int articleNo) throws SQLException;

	void modifyArticle(QnaDto qnaDto) throws SQLException;

	void deleteArticle(int articleNo) throws SQLException;

}
