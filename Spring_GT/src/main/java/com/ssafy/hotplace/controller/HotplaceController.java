package com.ssafy.hotplace.controller;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hotplace.model.HotplaceDto;
import com.ssafy.hotplace.model.HotplaceListDto;
import com.ssafy.hotplace.model.service.HotplaceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/board/hotplace")
@Api("핫플레이스  API ")
public class HotplaceController {
	private final Logger logger = LoggerFactory.getLogger(HotplaceController.class);

	private HotplaceService hotplaceService;

	public HotplaceController(HotplaceService hotplaceService) {
		super();
		this.hotplaceService = hotplaceService;
	}
	
	@ApiOperation(value = "핫플레이스 등록하기")
	@PostMapping
	public ResponseEntity<?> registHotplace(
			@RequestBody @ApiParam(value = "등록할 핫플레이스 정보.", required = true) HotplaceDto hotplaceDto) {
		logger.info("registHotplace hotplaceDto - {}", hotplaceDto);
		try {
			hotplaceService.registHotplace(hotplaceDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "핫플레이스 게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({@ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<?> listPlace() {
		logger.info("listPlace 호출!!");
		try {
			HotplaceListDto hotplaceListDto = hotplaceService.listPlace();
			System.out.println("HotplaceListDto 결과 : "+hotplaceListDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(hotplaceListDto);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	
	@ApiOperation(value = "핫플레이스 상세보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = HotplaceDto.class)
	@GetMapping("/detail/{contentId}")
	public ResponseEntity<?> getHotplaces
	(@PathVariable("contentId") @ApiParam(value = "상세보기할 핫플레이스 번호", required = true) int contentId) {
		logger.info("getHotplaces 호출!! {}", contentId);
		try {
			HotplaceListDto hotplaceListDto = hotplaceService.getHotplace(contentId);
			System.out.println("getHotplaces 결과 : "+hotplaceListDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(hotplaceListDto);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "핫플레이스 조회수 증가", notes = "해당 글 번호의 조회수를 증가한다.", response = String.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<String> updateHit(
			@PathVariable("articleno") @ApiParam(value = "증가할 게시글 번호.", required = true) int articleno)
			throws Exception {
		logger.info("updateHit - 호출 : " + articleno);
		hotplaceService.updateHit(articleno);
		return ResponseEntity.ok().build();
	}
	
	@ApiOperation(value="로그인한 사용자의 좋아요 클릭여부", notes="핫플레이스 게시판 접속시, 현재 로그인한 사용자가 게시글에 좋아요를 눌렀는지를 나타낸다.")
	@GetMapping("/{articleno}/{userId}")
	@ResponseBody
	public ResponseEntity<Integer> checkFavorite(@PathVariable("userId") String userId, 
			@PathVariable("articleno") String articleNo) throws Exception {
		logger.debug("checkFavorite userId : {}, articleNo : {}", userId, articleNo);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("articleNo", articleNo);
		map.put("userId", userId);
		
		int cnt = hotplaceService.checkFavorite(map);
		return new ResponseEntity<Integer>(cnt,HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아요 추가", notes = "글번호에 해당하는 좋아요 정보를 추가한다.")
	@PostMapping("/{articleno}/{userId}")
	public ResponseEntity<?> addFavorite(@PathVariable("userId") String userId, 
			@PathVariable("articleno") String articleNo) throws Exception {
		logger.info("addFavorite- 호출 userId : {}, articleNo : {}", userId, articleNo);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("articleNo", articleNo);
		map.put("userId", userId);
		
		try {
			// 이미 좋아요를 해당게시글에 눌렀는지 확인하기
			int cnt = hotplaceService.checkFavorite(map);
			if(cnt != 1) {
				// 좋아요를 한 적 없을 때만 좋아요 추가하기
				hotplaceService.addFavorite(map);
			}else {
				System.out.println("이전에 좋아요한 게시글 ");
			}
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "좋아요 삭제", notes = "글번호에 해당하는 좋아요 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}/{userId}")
	public ResponseEntity<?> deleteFavorite(@PathVariable("userId") String userId, 
			@PathVariable("articleno") String articleNo) throws Exception {
		logger.info("deleteFavorite- 호출  userId : {}, articleNo : {}", userId, articleNo);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("articleNo", articleNo);
		map.put("userId", userId);
		
		try {
			hotplaceService.deleteFavorite(map);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
