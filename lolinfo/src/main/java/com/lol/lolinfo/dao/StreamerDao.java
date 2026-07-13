package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.StreamerDto;
import com.lol.lolinfo.dto.StreamerTierDto;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.StreamerStatVO;
import com.lol.lolinfo.vo.StreamerTierVO;

@Repository
public class StreamerDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(StreamerDto streamerDto) {
		int streamerNo = sqlSession.selectOne("streamer.sequence");
		streamerDto.setStreamerNo(streamerNo);
		sqlSession.insert("streamer.insert", streamerDto);
	}
	
	//조회
	public List<StreamerStatVO> selectList(PageVO pageVO){
		return sqlSession.selectList("streamer.selectList", pageVO);
	}
	//조회(Order by 비공식포함)
	public List<StreamerStatVO> selectTotalRankingList(PageVO pageVO){
		return sqlSession.selectList("streamer.selectTotalRankingList",pageVO);
	}
	public int count() {
		return sqlSession.selectOne("streamer.count");
	}
	//검색
	public List<StreamerStatVO> searchStreamer(PageVO pageVO) {
		return sqlSession.selectList("streamer.searchStreamer",pageVO);
	}
	public int searchCount(String keyword) {
		return sqlSession.selectOne("streamer.searchCount",keyword);
	}
	// 검색용 자동완성 조회
	public List<StreamerDto> autoSearch(String keyword){
		return sqlSession.selectList("streamer.autoSearch",keyword);
	}
	
	
	//상세조회
	public StreamerDto selectOne(int streamerNo) {
		return sqlSession.selectOne("streamer.selectOne",streamerNo);
	}
	//상세조회
	public StreamerStatVO selectOnePlusStat(int streamerNo) {
		return sqlSession.selectOne("streamer.selectOnePlusStat",streamerNo);
	}
	
	//수정
	public void update(StreamerDto streamerDto) {
		sqlSession.update("streamer.update", streamerDto);
	}
	
	
	//삭제
	public boolean delete(int streamerNo) {
		return sqlSession.delete("streamer.delete",streamerNo)>0;
	}
	
	///-- 스트리머 티어표--------------------------
	// 티어 등록
	public void insertTier(StreamerTierDto streamerTierDto) {
		sqlSession.insert("streamerTier.insert", streamerTierDto);
	}
	// 티어 목록
	public List<StreamerTierVO> selectTierList(int tournamentId) {
		return sqlSession.selectList("streamerTier.selectList", tournamentId);
	}
	// 티어 수정
	public void updateTier(StreamerTierDto streamerTierDto) {
		sqlSession.update("streamerTier.updateTier", streamerTierDto);
	}
}
