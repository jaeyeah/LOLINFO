package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.MemberDto;

@Repository
public class MemberDao {

	@Autowired
	private SqlSession sqlSession;
	
	/// 등록(회원가입)
	public void insert(MemberDto memberDto) {
		// 비밀번호 암호화
		// 등록
		sqlSession.insert("member.insert", memberDto);
	}
	
	///조회
	// 기본조회(목록)
	public List<MemberDto> selectList(){
		return sqlSession.selectOne("member.selectList");
	}
	// 상세조회 (+아이디 중복 검사)
	public MemberDto selectOne(String memberId) {
		return sqlSession.selectOne("member.detail", memberId);
	}
	
	// 닉네임 중복 검사용 조회
	public MemberDto selectOneByMemberNickname(String memberNickname) {
		return sqlSession.selectOne("member.detailByNickname", memberNickname);
	}
	
	
	
	
	
}
