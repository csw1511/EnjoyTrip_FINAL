package com.ssafy.comment.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.CommentInputDto;
import com.ssafy.comment.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentMapper commentMapper;
	
	@Autowired
	public CommentServiceImpl(CommentMapper commentMapper) {
		super();
		this.commentMapper = commentMapper;
	}

	
	@Override
	@Transactional
	public void addComment(CommentDto commentDto) throws Exception {
		commentMapper.addComment(commentDto);
		
	}

	
	@Override
	public List<CommentDto> getCommentList(int articleNo) throws Exception {		
		return commentMapper.getCommentList(articleNo);
	}
	
	@Override
	public void updateComment(CommentInputDto commentInputDto) throws Exception {
		commentMapper.updateComment(commentInputDto);
		
	}
	
	@Override
	@Transactional
	public void deleteComment(int articleNo) throws Exception {
		commentMapper.deleteComment(articleNo);
	}


	

}
