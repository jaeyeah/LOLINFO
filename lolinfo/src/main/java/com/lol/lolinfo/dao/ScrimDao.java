package com.lol.lolinfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.ScrimDto;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.ScrimListVO;
import com.lol.lolinfo.vo.ScrimRecordVO;
import com.lol.lolinfo.vo.ScrimUpdateVO;
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
	public int count(int scrimTournament) {
		return sqlSession.selectOne("scrim.count", scrimTournament);
	}
	
	public List<ScrimListVO> selectList(PageVO pageVO){
	    return sqlSession.selectList("scrim.selectList", pageVO);
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
	/// ----- 수정 -----
	// 부분수정
	public boolean updateUnit(ScrimUpdateVO scrimUpdateVO) {
		return sqlSession.update("scrim.updateUnit", scrimUpdateVO)>0;
	}
	// 삭제
	public boolean delete(int scrimId) {
		return sqlSession.delete("scrim.delete", scrimId)>0;
	}
}

