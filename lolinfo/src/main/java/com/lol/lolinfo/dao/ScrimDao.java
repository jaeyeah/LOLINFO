package com.lol.lolinfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.ScrimDto;
import com.lol.lolinfo.vo.ScrimListVO;
import com.lol.lolinfo.vo.ScrimRecordVO;
import com.lol.lolinfo.vo.ScrimVsRecordVO;

@Repository
public class ScrimDao {

	@Autowired
	private SqlSession sqlSession;
	
	// 스크림 등록
	public void insert(ScrimDto scrimDto) {
		sqlSession.insert("scrim.insert",scrimDto);
	}
	
	// 목록 조회
	public List<ScrimListVO> selectList(int scrimTournament){
	    return sqlSession.selectList("scrim.selectList", scrimTournament);
	}

	public List<ScrimRecordVO> selectRecordList(int scrimTournament){
	    return sqlSession.selectList("scrim.selectRecordList", scrimTournament);
	}
	// 팀별 상대전적 조회
	public List<ScrimVsRecordVO> selectVsList(int scrimTournament, int scrimTeam){
		Map<String, Object> param = new HashMap<>();
	    param.put("scrimTournament", scrimTournament);
	    param.put("scrimTeam", scrimTeam);
	    return sqlSession.selectList("scrim.selectVsRecordList", param);
	}

}

