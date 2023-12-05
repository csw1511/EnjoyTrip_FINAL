package com.ssafy.board.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardInputDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.FileInfoDto;
import com.ssafy.board.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@Api("게시판 컨트롤러  API ")
public class BoardController {
	
	private final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Value("${file.path}")
	private String uploadPath;
	
	@Value("${file.path.upload-images}")
	private String uploadImagePath;
	
	@Value("${file.path.upload-files}")
	private String uploadFilePath;


	private BoardService boardService;

	public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}
	
	@ApiOperation(value="게시판 글 작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping
	public ResponseEntity<?> writeArticle(@ApiParam(value="게시글 정보", required = true) MultipartHttpServletRequest mhsRequest) {
		logger.info("writeArticle mhsRequest : {}", mhsRequest);
		try {
			ObjectMapper oMapper = new ObjectMapper();  // 나중에 찾아보기
			
			BoardInputDto boardInputDto = oMapper.readValue(mhsRequest.getParameter("article"), BoardInputDto.class);		// 게시글 정보
			System.out.println("게시글정보 : "+boardInputDto.toString());
			
			System.out.println(mhsRequest.getFiles("images"));
			
//			FileUpload 관련 설정.
			List<MultipartFile> multipartFiles = mhsRequest.getFiles("images");		// List<MultipartFile>을 반환함.
//			System.out.println(multipartFiles.get(0));
			
//			logger.debug("uploadPath : {}, uploadImagePath : {}, uploadFilePath : {}", uploadPath, uploadImagePath, uploadFilePath);
			logger.debug("MultipartFile.isEmpty : {}", multipartFiles.isEmpty());
			if (!multipartFiles.isEmpty() && !multipartFiles.get(0).isEmpty()) {
				// 업로드 할 파일이 있을경우 
//				String realPath = servletContext.getRealPath(UPLOAD_PATH);
//				String realPath = servletContext.getRealPath("/resources/img");
				String today = new SimpleDateFormat("yyMMdd").format(new Date());
				String saveFolder = uploadPath + File.separator + today;		// 저장폴더 만들기
				logger.debug("저장 폴더 : {}", saveFolder);
				File folder = new File(saveFolder);
				if (!folder.exists())
					folder.mkdirs();
				
				List<FileInfoDto> fileInfos = new ArrayList<FileInfoDto>();
				for (MultipartFile mfile : multipartFiles) {
					FileInfoDto fileInfoDto = new FileInfoDto();
					String originalFileName = mfile.getOriginalFilename();
					
					if (!originalFileName.isEmpty()) {
						String saveFileName = UUID.randomUUID().toString()
								+ originalFileName.substring(originalFileName.lastIndexOf('.'));
						
						fileInfoDto.setSaveFolder(today);
						fileInfoDto.setOriginalFile(originalFileName);
						fileInfoDto.setSaveFile(saveFileName);
						logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
						mfile.transferTo(new File(folder, saveFileName));
					}
					fileInfos.add(fileInfoDto);
				}
				boardInputDto.setFileInfos(fileInfos);
			}
			
			boardService.writeArticle(boardInputDto);
		
			System.out.println("나오냐?");
			System.out.println(boardInputDto);
		        
//			
//			
//			List<FileInfoDto> fileInfos = mhsRequest.getFiles("images");
//			boardInputDto.setFileInfos(fileInfos);
//			System.out.println("itemName : "+ itemName);
			
			
//			boardService.writeArticle(boardInputDto);
//			return new ResponseEntity<Void>(HttpStatus.CREATED);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardInputDto);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({@ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<?> listArticle(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		logger.info("listArticle map - {}", map);
		try {
			BoardListDto boardListDto = boardService.listArticle(map);
			System.out.println("결과 : "+boardListDto);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/{articleno}")
	public ResponseEntity<BoardDto> getArticle(
			@PathVariable("articleno") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleno)
			throws Exception {
		logger.info("getArticle - 호출 : " + articleno);
		boardService.updateHit(articleno);
		
		BoardDto boardDto = boardService.getArticle(articleno);
//		System.out.println(boardService.fileInfoList(articleno));
		List<FileInfoDto> fileInfos = boardService.fileInfoList(articleno);	// 해당 게시글의 파일 정보 가져오기
//		boardDto.setFileInfos(fileInfos);	// 해당 게시글의 파일 정보 담기			
//		if(!fileInfos.isEmpty()) {
//			Resource resource = new FileSystemResource(path+foler+filname);
//			HttpHeaders header = new HttpHeaders();
//			
////			header.add("Contnet-type", headerValue);
//			// 상세 조회할 게시글에 보여줄 파일이 있을경우
//			for(int i = 0; i < fileInfos.size(); i++) {
////				fileInfos.get(i).setSaveFolder(uploadPath+fileInfos.get(i).getSaveFolder());		// 파일을 저장한 폴더이름 변경하기
//				System.out.println("Dfdf : "+fileInfos.get(i));
//				
//			}
//		}
		boardDto.setFileInfos(fileInfos);
		System.out.println(boardDto);
		return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
	}
	
	 @GetMapping("/getImg/{saveFolder}/{originalFile}/{saveFile}")
	    public ResponseEntity<?> getImg(@PathVariable("saveFolder") String saveFolder, 
	                                            @PathVariable("originalFile") String originalFile, 
	                                            @PathVariable("saveFile") String saveFile)  {

	        String file = uploadPath + File.separator +saveFolder + File.separator + saveFile;
	        
	        try {
	            Path filePath = Paths.get(file);
	            
	            Resource resource = new FileSystemResource(file);
	            HttpHeaders headers = new HttpHeaders();
	            headers.add("Content-type", Files.probeContentType(filePath));
	            
	            System.out.println("resource : " + resource);
	            
	            return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        
	    } 
	
	
	
	
	@ApiOperation(value = "게시글 좋아요", notes = "게시글을 나갈때 좋아요 수를 업데이트한다.", response = BoardDto.class)
	@GetMapping("/{articleno}/{favoritecount}")
	public ResponseEntity<String> updateFavorite(
			@PathVariable("articleno") @ApiParam(value = "좋아요 수를 업데이트 할 글번호.", required = true) int articleno, 
			@PathVariable("favoritecount") @ApiParam(value = "업데이트할 좋아요 수", required = true) int favoritecount)
			throws Exception {
		logger.info("updateFavorite - 호출 : " + articleno+", "+favoritecount);
		HashMap<String, Integer> map = new HashMap<String, Integer>( );
		map.put("articleNo", articleno);
		map.put("favorite", favoritecount);
		boardService.updateFavorite(map);

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "수정 할 글 얻기", notes = "수정할 글 번호에 해당하는 게시글의 정보를 반환한다.", response = BoardDto.class)
	@GetMapping("/modify/{articleno}")
	public ResponseEntity<BoardDto> getModifyArticle(
			@PathVariable("articleno") @ApiParam(value = "수정할 글의 글 번호.", required = true) int articleno)
			throws Exception {
		logger.info("getModifyArticle - 호출 : {}", articleno);
		return new ResponseEntity<BoardDto>(boardService.getArticle(articleno), HttpStatus.OK);
	}

	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) BoardInputDto boardInputDto) throws Exception {
		logger.info("modify boardInputDto : {}", boardInputDto);
		boardService.modifyArticle(boardInputDto);
		
		return ResponseEntity.ok().build();

	}

	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{articleno}")
	public ResponseEntity<String> deleteArticle(
			@PathVariable("articleno") @ApiParam(value = "삭제할 글의 글번호.", required = true) int articleno) throws Exception {
		logger.info("delete articleNo : {}", articleno);
		boardService.deleteArticle(articleno);
		return ResponseEntity.ok().build();
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ApiOperation(value = "메인화면 회전목마", response = String.class)
	@GetMapping("/main/recent")
	public ResponseEntity<?> getRecentCourse() throws Exception {

		logger.info("getRecentCourse");
		List<BoardDto> dto = boardService.getRecentCourse();
		System.out.println("결과 : "+dto);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return ResponseEntity.ok().headers(header).body(dto);
	}
	
//	@GetMapping("/main/place")
//	public ResponseEntity<String> getTopPlace() throws Exception {
//		return ResponseEntity.ok().build();
//	}
//	
//	@GetMapping("/{articleno}")
//	public ResponseEntity<String> getRecentCourse(
//			@PathVariable("articleno") @ApiParam(value = "삭제할 글의 글번호.", required = true) int articleno) throws Exception {
//		logger.info("delete articleNo : {}", articleno);
//		boardService.deleteArticle(articleno);
//		return ResponseEntity.ok().build();
//	}
//	
}
