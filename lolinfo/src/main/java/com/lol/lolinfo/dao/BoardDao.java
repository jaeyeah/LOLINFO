package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.BoardDto;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	//등록
	public int sequence() {
		return sqlSession.selectOne("board.sequence");
	}
	public void insert(BoardDto boardDto) {
		sqlSession.insert("board.insert",boardDto);
	}
	//목록
	public List<BoardDto> selectList(){
		return sqlSession.selectList("board.selectList");
	}
	// 상세
	public BoardDto selectOne(int boardId) {
		return sqlSession.selectOne("board.selectOne",boardId);
	}
}
