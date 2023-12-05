package com.ssafy.comment.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.CommentInputDto;

@Mapper
public interface CommentMapper {
	
	void addComment(CommentDto commentDto) throws SQLException;

	List<CommentDto> getCommentList(int articleNo) throws SQLException;
	
	void updateComment(CommentInputDto commentInputDto) throws SQLException;
	
	void deleteComment(int articleNo) throws SQLException;
	
	
}
