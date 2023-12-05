package com.ssafy.board.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardInputDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.FileInfoDto;

public interface BoardService {
	
	void writeArticle(BoardInputDto boardInputDto) throws Exception;
	
	BoardListDto listArticle(Map<String, String> map) throws Exception;
	
//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	
	BoardDto getArticle(int articleNo) throws Exception;
	
	void updateHit(int articleNo) throws Exception;
	
	void updateFavorite(Map<String, Integer> map)throws Exception;
	
	void modifyArticle(BoardInputDto boardInputDto) throws Exception;
	
	void deleteArticle(int articleNo) throws Exception;
	
	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
	
	List<BoardDto> getRecentCourse() throws Exception;
}
