package com.ssafy.comment.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.comment.model.CommentDto;
import com.ssafy.comment.model.CommentInputDto;
import com.ssafy.comment.model.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("board/comment")
@Api("Comment Controller")
public class CommentController {
	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	
	@ApiOperation(value = "댓글 등록")
	@PostMapping("/{articleno}")
	public ResponseEntity<?> addComment(
			@RequestBody @ApiParam(value = "게시글 정보.", required = true) CommentDto commentDto) {
		logger.info("addComment commentDto - {}", commentDto);
		try {
			commentService.addComment(commentDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시글 번호를 받아서 해당 게시글에 달린 댓글 목록을 반환한다.", response = CommentDto.class)
	@GetMapping("/{articleno}")
	public List<CommentDto> getCommentList(@PathVariable("articleno") @ApiParam(value = "댓글을 불러올 게시글 번호.", required = true) int articleNo) throws Exception { 
		logger.info("getCommentList articleno - {}", articleNo);
		System.out.println(commentService.getCommentList(articleNo));
		
		return commentService.getCommentList(articleNo);
	}
	
	@ApiOperation(value = "댓글 수정", notes = "해당하는 댓글의 내용을 수정한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> updateComment(@RequestBody @ApiParam(value = "수정할 댓글 정보.", required = true) CommentInputDto commentInputDto) throws Exception {
		logger.info("updateComment CommentInputDto - {}: ", commentInputDto);
		commentService.updateComment(commentInputDto);
		return ResponseEntity.ok().build();

	}
	
	@ApiOperation(value = "댓글 삭제", notes = "글번호에 해당하는 댓글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{commentid}")
	public ResponseEntity<String> deleteComment(@PathVariable("commentid") @ApiParam(value = "삭제할 댓글 번호.", required = true) int commentId) throws Exception {
		logger.info("deleteComment - 호출");
		commentService.deleteComment(commentId);
		return ResponseEntity.ok().build();

	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
