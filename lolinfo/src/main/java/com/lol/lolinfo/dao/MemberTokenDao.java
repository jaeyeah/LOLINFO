package com.lol.lolinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.MemberTokenDto;

@Repository
public class MemberTokenDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(MemberTokenDto memberTokenDto) {
		sqlSession.insert("memberToken.insert", memberTokenDto);
	}
	
	public MemberTokenDto selectOne(MemberTokenDto memberTokenDto) {
		return sqlSession.selectOne("memberToken.detail", memberTokenDto);
	}
	
	public boolean delete(Long memberTokenNo) {
		return sqlSession.delete("memberToken.delete", memberTokenNo)>0;
	}
	
	public boolean deleteByTarget(String memberTokenTarget) {
		return sqlSession.delete("memberToken.deleteByTarget", memberTokenTarget)>0;
	}
}
