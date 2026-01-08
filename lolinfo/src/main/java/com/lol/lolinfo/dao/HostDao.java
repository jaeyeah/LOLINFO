package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.HostDto;
import com.lol.lolinfo.vo.HostListVO;

@Repository
public class HostDao {

	@Autowired
	private SqlSession sqlSession;
	
	//개최자 등록
	public void insert(HostDto hostDto) {
		sqlSession.insert("host.insert",hostDto);
	}
	
	//목록 조회 (대회별 개최자)
	public List<HostListVO> selectListByTournamentId(int tournamentId){
		return sqlSession.selectList("host.selectListByTournamentId",tournamentId);
	}
	
	//목록 조회 (스트리머별 개최목록)
	public List<HostListVO> selectListByStreamerNo(int streamerNo){
		return sqlSession.selectList("host.selectListByStreamerNo",streamerNo);
	}
	
	
}
