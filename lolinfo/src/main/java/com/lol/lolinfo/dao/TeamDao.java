package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.TeamDto;
import com.lol.lolinfo.vo.StreamerTeamListVO;
import com.lol.lolinfo.vo.TeamListVO;

@Repository
public class TeamDao {

	@Autowired
	private SqlSession sqlSession;
	
	// 등록
	public void insert(TeamDto teamDto) {
		int teamId = sqlSession.selectOne("team.sequence");
		teamDto.setTeamId(teamId);
		sqlSession.insert("team.insert", teamDto);
	}
	
	// 대회별 상세조회
	public List<TeamListVO> selectList(int tournamentId){
		return sqlSession.selectList("team.selectListByTournamentId", tournamentId);
	}
	
	// 스트리머별 상세조회
	public List<StreamerTeamListVO> selectListByStreamerNo(int streamerNo){
		return sqlSession.selectList("team.selectListByStreamerNo", streamerNo);
	}
}
