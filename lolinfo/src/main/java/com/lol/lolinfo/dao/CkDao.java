package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.CkDto;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.vo.CkListVO;
import com.lol.lolinfo.vo.CkVO;
import com.lol.lolinfo.vo.PageVO;

@Repository
public class CkDao {

	@Autowired
	private SqlSession sqlSession;

	/// ----- 등록 -----
	public int sequence() {
		return sqlSession.selectOne("ck.sequence");
	}
	public void insert(CkVO ckVO) {
		sqlSession.insert("ck.insert",ckVO);
	}
	public void insertParticipant(CkParticipantDto participantDto) {
		sqlSession.insert("ck.insertParticipant", participantDto);
	}
	
	/// ----- 목록 조회 -----
	//전체 CK 조회
	public List<CkDto> selectList(PageVO pageVO){
		return sqlSession.selectList("ck.selectList",pageVO);
	}
	public int count() {
		return sqlSession.selectOne("ck.count");
	}
	
	
	
	// 스트리머가 참여한 CK
	public List<CkListVO> selectStreamer(int streamerNo){
		return sqlSession.selectList("ck.selectStreamer", streamerNo);
	}
	

}
