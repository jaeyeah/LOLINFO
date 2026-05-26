package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.CkDto;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.vo.CkListVO;
import com.lol.lolinfo.vo.CkParticipantVO;
import com.lol.lolinfo.vo.CkVO;
import com.lol.lolinfo.vo.CkVsVO;
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
	// 확인용 단건 조회
	public CkDto selectOne(int ckId) {
		return sqlSession.selectOne("ck.selectOne");
	}
	
	//전체 CK 조회
	public List<CkDto> selectList(PageVO pageVO){
		return sqlSession.selectList("ck.selectList",pageVO);
	}
	public int count() {
		return sqlSession.selectOne("ck.count");
	}
	
	// CK별 참여 스트리머 10명 조회
	public List<CkParticipantVO> selectParticipantList(int ckId){
		return sqlSession.selectList("ck.selectParticipant", ckId);
	}
	
	/// 상세 조회
	public List<CkListVO> selectListByStreamer(PageVO pageVO){
		return sqlSession.selectList("ck.selectListByStreamer",pageVO);
	}
	public int countByStreamer(int stremaerNo) {
		return sqlSession.selectOne("ck.countByStreamer", stremaerNo);
	}
	
	// 조회 맞라인 상대 전적
	public List<CkVsVO> selectVsList(int streamerNo){
		return sqlSession.selectList("ck.selectVsList", streamerNo);
	}
	
	/// ----- 수정 -----
	// 부분수정
	public boolean updateUnit(CkDto ckDto) {
		return sqlSession.update("ck.updateUnit", ckDto)>0;
	}

	
	/// ----- 삭제 -----
	public void delete(int ckId) {
		sqlSession.delete("ck.delete", ckId);
	}
}
