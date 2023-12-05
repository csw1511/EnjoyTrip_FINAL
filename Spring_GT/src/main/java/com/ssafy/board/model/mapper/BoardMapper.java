package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardInputDto;
import com.ssafy.board.model.FileInfoDto;

@Mapper
public interface BoardMapper {

	void writeArticle(BoardInputDto boardInputDto) throws SQLException;
	
	void registerFile(BoardInputDto boardInputDto) throws Exception;
	
	List<BoardDto> listArticle(Map<String, Object> param) throws SQLException;

	int getTotalArticleCount(Map<String, Object> param) throws SQLException;

	BoardDto getArticle(int articleNo) throws SQLException;

	void updateHit(int articleNo) throws SQLException;

	void updateFavorite(Map<String, Integer> map)throws SQLException;
	
	void modifyArticle(BoardInputDto boardInputDto) throws SQLException;
	
	void deleteFile(int articleNo) throws Exception;
	
	void deleteArticle(int articleNo) throws SQLException;
	
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;

	List<BoardDto> getRecentCourse() throws SQLException;
}
