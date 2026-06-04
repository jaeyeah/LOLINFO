package com.lol.lolinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.BoardDto;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.vo.CkVO;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	//등록
	public int sequence() {
		return sqlSession.selectOne("board.sequence");
	}
	public void insert(BoardDto boardDto) {
		sqlSession.insert("board.insert");
	}


}
