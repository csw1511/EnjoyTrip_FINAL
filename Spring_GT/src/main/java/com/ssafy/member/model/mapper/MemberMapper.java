package com.ssafy.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.LoginDto;
import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.MemberModifyPwDto;
@Mapper
public interface MemberMapper {
	void joinMember(MemberDto memberDto) throws SQLException;
	MemberDto loginMember(LoginDto loginDto) throws SQLException;
	void modifyMember(MemberDto memberDto) throws SQLException;
	int idCheck(String userId) throws SQLException;
	int findPwd(Map<String, String> map) throws SQLException;
	void deleteMember(String userId) throws SQLException;
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String userid) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
	MemberDto userInfo(String userId) throws SQLException;
	void modifyMemberPw(MemberModifyPwDto memberModifyPwDto) throws SQLException;
}
