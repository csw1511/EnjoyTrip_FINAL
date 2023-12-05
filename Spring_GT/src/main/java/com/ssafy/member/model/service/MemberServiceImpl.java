package com.ssafy.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.member.model.LoginDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.MemberModifyPwDto;
import com.ssafy.member.model.mapper.MemberMapper;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Service
public class MemberServiceImpl implements MemberService {
	
	private MemberMapper memberMapper;
	
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
	@Transactional
	public void joinMember(MemberDto memberDto) throws Exception {
		//비밀번호 암호화
		String password = memberDto.getUserPassword();
		String encrypted = BCrypt.hashpw(password, BCrypt.gensalt());
		memberDto.setUserPassword(encrypted);
		memberMapper.joinMember(memberDto);
		
	}

	@Override
	public MemberDto loginMember(LoginDto loginDto) throws Exception {
		MemberDto nowDto = memberMapper.loginMember(loginDto);
		if(nowDto == null) return null;
		if(BCrypt.checkpw(loginDto.getUserPassword(), nowDto.getUserPassword())){
//			System.out.println(BCrypt.checkpw(nowDto.getUserPassword(), loginDto.getUserPassword()));
//			System.out.println(nowDto);
			return nowDto;
		}else {
//			System.out.println(BCrypt.checkpw(nowDto.getUserPassword(), loginDto.getUserPassword()));
//			System.out.println(nowDto);
			return null;			
		}
	}

	@Override
	@Transactional
	public void modifyMember(MemberDto memberDto) throws Exception {
		memberMapper.modifyMember(memberDto);
	}

	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}

	@Override
	public int findPwd(Map<String, String> map) throws Exception {
		return memberMapper.findPwd(map);
	}

	@Override
	public void deleteMember(String userId) throws Exception {
		memberMapper.deleteMember(userId);
		
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}

	@Override
	public MemberDto userInfo(String userId) throws Exception{
		return memberMapper.userInfo(userId);
	}

	@Override
	public void modifyMemberPw(MemberModifyPwDto memberModifyPwDto) throws Exception {
		String passwordOld = memberModifyPwDto.getUserPasswordOld();
		String passwordNew = memberModifyPwDto.getUserPasswordNew();
		String encryptedNew = BCrypt.hashpw(passwordNew, BCrypt.gensalt());
		memberModifyPwDto.setUserPasswordNew(encryptedNew);
		
		LoginDto loginDto = new LoginDto();
		loginDto.setUserId(memberModifyPwDto.getUserId());
		

		MemberDto nowDto = memberMapper.loginMember(loginDto);
		if(BCrypt.checkpw(passwordOld, nowDto.getUserPassword())){
			memberMapper.modifyMemberPw(memberModifyPwDto);
		}
		
	}
}
