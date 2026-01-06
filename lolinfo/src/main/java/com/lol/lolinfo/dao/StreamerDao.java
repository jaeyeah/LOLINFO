package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.StreamerDto;
import com.lol.lolinfo.vo.StreamerStatVO;

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
	public List<StreamerStatVO> selectList(){
		return sqlSession.selectList("streamer.selectList");
	}
	//조회(Order by 비공식포함)
	public List<StreamerStatVO> selectTotalRankingList(){
		return sqlSession.selectList("streamer.selectTotalRankingList");
	}
	
	
	//상세조회
	public StreamerDto selectOne(int streamerNo) {
		return sqlSession.selectOne("streamer.selectOne",streamerNo);
	}
	
	//삭제
	public boolean delete(int streamerNo) {
		return sqlSession.delete("streamer.delete",streamerNo)>0;
	}
}
