package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.TournamentDto;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.TournamentListVO;

@Repository
public class TournamentDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(TournamentDto tournamentDto) {
		int tournamentId = sqlSession.selectOne("tournament.sequence");
		tournamentDto.setTournamentId(tournamentId);
		sqlSession.insert("tournament.insert", tournamentDto);
	}
	
	//조회
	public List<TournamentListVO> selectList(PageVO pageVO){
		return sqlSession.selectList("tournament.selectList",pageVO);
	}
	public int count() {
		return sqlSession.selectOne("tournament.count");
	}
	
	//상세조회
	public TournamentDto selectOne(int tounnamentId) {
		return sqlSession.selectOne("tournament.selectOne",tounnamentId);
	}
	
	//수정
	public void update(TournamentDto tournamentDto) {
		sqlSession.update("tournament.update", tournamentDto);
	}
	
}
