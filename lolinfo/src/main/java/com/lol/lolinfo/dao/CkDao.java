package com.lol.lolinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.vo.CkVO;

@Repository
public class CkDao {

	@Autowired
	private SqlSession sqlSession;

	/// 등록
	public int sequence() {
		return sqlSession.selectOne("ck.sequence");
	}
	public void insert(CkVO ckVO) {
		sqlSession.insert("ck.insert",ckVO);
	}
	public void insertParticipant(CkParticipantDto participantDto) {
		sqlSession.insert("ck.insertParticipant", participantDto);
	}
	
	

}
