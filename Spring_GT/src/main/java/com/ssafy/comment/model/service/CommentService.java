package com.ssafy.comment.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.CommentInputDto;

public interface CommentService {
	
	void addComment(CommentDto commentDto) throws Exception;

	List<CommentDto> getCommentList(int articleNo) throws Exception;
	
	void updateComment(CommentInputDto commentInputDto) throws Exception;
	
	void deleteComment(int articleNo) throws Exception;

}
