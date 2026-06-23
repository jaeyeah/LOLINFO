package com.lol.lolinfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.vo.StreamerWithCkVO;
import com.lol.lolinfo.vo.StreamerWithTournamentDetailVO;
import com.lol.lolinfo.vo.StreamerWithTournamentVO;

@Repository
public class StreamerWithDao {

	@Autowired
	private SqlSession sqlSession;
	
	//같이 CK한 스트리머 조회
	public List<StreamerWithCkVO> withCk(int streamerNo) {
	    return sqlSession.selectList("streamerWith.withCk", streamerNo);
	}
	//같이 대회한 스트리머 조회
	public List<StreamerWithTournamentVO> withTournament(int streamerNo) {
	    return sqlSession.selectList("streamerWith.withTournament", streamerNo);
	}
	// --> 대회별 상세
	public List<StreamerWithTournamentDetailVO> withTournamentDetail(int streamerNo, int partnerNo) {
		Map<String, Object> param = new HashMap<>();
	    param.put("streamerNo", streamerNo);
	    param.put("partnerNo", partnerNo);
		return sqlSession.selectList("streamerWith.withTournamentDetail", param);
	}
}
