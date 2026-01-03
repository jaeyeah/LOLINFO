package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.StreamerDto;
import com.lol.lolinfo.dto.TeamDto;
import com.lol.lolinfo.vo.StreamerTeamListVO;
import com.lol.lolinfo.vo.TeamListVO;
import com.lol.lolinfo.vo.TeamResponseVO;

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
	// 등록시 check -> 스트리머 이름 조회해서 중복확인 + streamerNo 같이 반환
	public TeamResponseVO checkAndConvert(String streamerName) {
		StreamerDto findDto = sqlSession.selectOne("streamer.selectByStreamerName", streamerName);
	    // 등록된 스트리머가 있는지 확인해서 번호와, 중복여부를 vo에 담아 보내기
		//if(findDto==null) throw Exception;
		return TeamResponseVO.builder()
					.streamerNo(findDto.getStreamerNo())
					.streamerSoopId(findDto.getStreamerSoopId())
					.check(true)
				.build();
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
