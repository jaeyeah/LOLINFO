package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.VisitDto;
import com.lol.lolinfo.vo.VisitListVO;

@Repository
public class VisitDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(VisitDto visitDto) {
		sqlSession.insert("visit.insert",visitDto);
	}
	//수정 - 로그인시 변경
	public boolean updateLogin(VisitDto visitDto) {
		return sqlSession.update("visit.updateLogin",visitDto) > 0;	
	}
	//조회
	public List<VisitListVO> selectList(){
		return sqlSession.selectList("visit.selectList");
	}



}
