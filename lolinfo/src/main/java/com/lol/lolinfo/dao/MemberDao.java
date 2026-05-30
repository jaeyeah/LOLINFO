package com.lol.lolinfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.MemberDto;
import com.lol.lolinfo.vo.PageVO;

@Repository
public class MemberDao {

	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/// 등록(회원가입)
	public void insert(MemberDto memberDto) {
		// 비밀번호 암호화
		String origin = memberDto.getMemberPw();
		String encoded = passwordEncoder.encode(origin); // 암호화
		memberDto.setMemberPw(encoded);
		// 등록
		sqlSession.insert("member.insert", memberDto);
	}
	
	///조회
	// 기본조회(목록)
	public List<MemberDto> selectList(){
		return sqlSession.selectOne("member.selectList");
	}
	public List<MemberDto> selectListWithPaging(PageVO pageVO){
		return sqlSession.selectList("member.selectListWithPaging");
	}
	public int countMember() {
		return sqlSession.selectOne("member.count");
	}
	// - 검색조회
	public List<MemberDto> selectSearchList(String type, String keyword, PageVO pageVO) {
		Map<String, Object> params = new HashMap<>();
		params.put("pageVO", pageVO);
		params.put("type", type);
		params.put("keyword", keyword);
		return sqlSession.selectList("member.selectSearchList", params);
	}
	public int countSearchMember(String type, String keyword) {
		return sqlSession.selectOne("member.countSearchMember");
	}
	
	
	
	// 상세조회 (+아이디 중복 검사)
	public MemberDto selectOne(String memberId) {
		return sqlSession.selectOne("member.detail", memberId);
	}
	
	// 닉네임 중복 검사용 조회
	public MemberDto selectOneByMemberNickname(String memberNickname) {
		return sqlSession.selectOne("member.detailByNickname", memberNickname);
	}
	
	/// 삭제 (회원탈퇴)
	public boolean delete(String memberId) {
		return sqlSession.delete("member.delete", memberId) > 0;
	}
	
}
