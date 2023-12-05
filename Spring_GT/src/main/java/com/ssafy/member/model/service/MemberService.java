package com.ssafy.member.model.service;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.member.model.LoginDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.MemberModifyPwDto;

public interface MemberService {
	void joinMember(MemberDto memberDto) throws Exception;
	MemberDto loginMember(LoginDto loginDto) throws Exception;
	void modifyMember(MemberDto memberDto) throws Exception;
	int idCheck(String userId) throws Exception;
	int findPwd(Map<String, String> map) throws Exception;
	void deleteMember(String userId) throws Exception;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
	Object getRefreshToken(String userId) throws Exception;
	void deleRefreshToken(String userId) throws Exception;
	MemberDto userInfo(String userId) throws Exception;
	void modifyMemberPw(MemberModifyPwDto memberModifyPwDto) throws Exception;
}
