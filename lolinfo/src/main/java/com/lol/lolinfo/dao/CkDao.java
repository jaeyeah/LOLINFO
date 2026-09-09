package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.CkDto;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.vo.CkListVO;
import com.lol.lolinfo.vo.CkMonthlyCountVO;
import com.lol.lolinfo.vo.CkMyPageVO;
import com.lol.lolinfo.vo.CkParticipantVO;
import com.lol.lolinfo.vo.CkPeriodVO;
import com.lol.lolinfo.vo.CkRankingVO;
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
	public void insertParticipantAll(List<CkParticipantDto> participants) {
		sqlSession.insert("ck.insertParticipantAll", participants);
	}
	
	/// ----- 목록 조회 -----
	// 확인용 단건 조회
	public CkDto selectOne(int ckId) {
		return sqlSession.selectOne("ck.selectOne",ckId);
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
	public List<CkListVO> selectListByStreamer(CkPeriodVO query){
		return sqlSession.selectList("ck.selectListByStreamer", query);
	}
	public int countByStreamer(CkPeriodVO query) {
		return sqlSession.selectOne("ck.countByStreamer", query);
	}
	/// 검색
//	public List<CkListVO> selectSearchListByStreamer(PageVO pageVO){
//		return sqlSession.selectList("ck.selectSearchListByStreamer",pageVO);
//	}
//	public int countSearchByStreamer(int stremaerNo) {
//		return sqlSession.selectOne("ck.countSearchByStreamer", stremaerNo);
//	}
	
	// 조회 맞라인 상대 전적
	public List<CkVsVO> selectVsList(CkPeriodVO query){
		return sqlSession.selectList("ck.selectVsList", query);
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
	
	// 마이페이지용 조회
	public List<CkMyPageVO> selectMyPage(PageVO pageVO){
		return sqlSession.selectList("ck.mypage",pageVO);
	}
	
	// 월별 랭킹용 조회
	@Cacheable(value = "ckMonthlyRanking",key = "#month")
	public List<CkRankingVO> selectRanking(String month){
		return sqlSession.selectList("ck.selectMonthRanking",month);
	}
	// 월별 카운트 조회
	@Cacheable(value = "ckMonthlyCount",key = "#year")
	public List<CkMonthlyCountVO> selectMonthlyCount(int year){
		return sqlSession.selectList("ck.selectMonthlyCount",year);
	}
}
