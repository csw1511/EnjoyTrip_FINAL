package com.ssafy.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

import com.ssafy.member.model.LoginDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.MemberModifyPwDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//@Controller
@RestController
@RequestMapping("/member")
@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@Api(tags = {"회원 관리 "})
public class MemberController {
	
	private final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	private MemberService memberService;
	private JWTUtil jwtUtil;

	
	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		super();
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}

	
	@ApiOperation(value="회원 가입", notes="회원의 정보를 받아 처리")
	@PostMapping("/join")
	public ResponseEntity<?> joinMember(@RequestBody @ApiParam(value="회원 정보.", required=true) MemberDto memberDto) {
		logger.debug("memberDto info : {}", memberDto);
		try {
			memberService.joinMember(memberDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value="아이디 중복확인", notes="회원 가입시 입력한 아이디 중복확인")
	@GetMapping("/join/{userId}")
	@ResponseBody
	public ResponseEntity<Integer> idCheck(@PathVariable("userId") String userId) throws Exception {
		logger.debug("idCheck userId : {}", userId);
		int cnt = memberService.idCheck(userId);
		return new ResponseEntity<Integer>(cnt,HttpStatus.OK);
	}
	

	@ApiOperation(value="로그인", notes="사용자 로그인 확인")
	@PostMapping("/login")
	public ResponseEntity<?> loginMember
	(@RequestBody @ApiParam(value = "로그인할 회원 정보") LoginDto loginDto) throws Exception {
		logger.debug("login loginDto : {}", loginDto);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			MemberDto memberDto = memberService.loginMember(loginDto);
			System.out.println("로그인확인해보자");
			System.out.println(memberDto);
			if(memberDto != null) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(memberDto.getUserId());
				logger.debug("access token : {}", accessToken);
				logger.debug("refresh token : {}", refreshToken);

				memberService.saveRefreshToken(memberDto.getUserId(), refreshToken);
				
//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
				
//				return new ResponseEntity<>(memberDto, HttpStatus.OK);
			}else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			logger.debug("로그인 에러 발생 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value="로그아웃", notes="사용자의 로그아웃을 확인하고 회원정보를 담은 Token을 제거한다")
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> logoutMember(
			@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId
			) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	@ApiOperation(value="회원 정보 수정", notes="로그인한 사용자의 회원 정보를 수정한다.")
	@PostMapping("/modify")
	public ResponseEntity<String>  modifyMember(@RequestBody @ApiParam(value = "수정할 회원 정보") MemberDto memberDto) {
		logger.debug("memberDto info : {}", memberDto);
		try {
			memberService.modifyMember(memberDto);
//			session.setAttribute("userinfo", memberDto);
			return new ResponseEntity<String>(HttpStatus.CREATED);
//			return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value="회원 비밀번호 변경", notes="로그인한 회원의 비밀번호를 변경한다.")
	@PostMapping("/modifyPw")
	public ResponseEntity<String> modifyMemberPw(@RequestBody @ApiParam(value="바꿀 비밀번호와 기존 비밀번호") MemberModifyPwDto memberModifyPwDto){
		logger.debug("memberModifyPwDto info : {}", memberModifyPwDto);
		try{
			memberService.modifyMemberPw(memberModifyPwDto);
			return new ResponseEntity<String>(HttpStatus.CREATED);
		} catch(Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@ApiOperation(value="회원 정보 삭제", notes="로그인한 회원의 정보를 삭제한다.")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteMember(@PathVariable("userId") @ApiParam(value = "탈퇴할 회원아이디", required = true) String userId, HttpSession session) {
		logger.info("delete member - 호출");
		
		try {
			memberService.deleteMember(userId);
			if(session.getAttribute("userinfo") != null) {
				session.removeAttribute("userinfo");
				session.invalidate();
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
		return  ResponseEntity.ok().build();
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getUserId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불가!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	} 
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userId);
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

}
