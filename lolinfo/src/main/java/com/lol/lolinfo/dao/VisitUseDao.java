package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.VisitUseDto;

@Repository
public class VisitUseDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록+추가(merge)
	public void increase(String useType) {
        sqlSession.update("visit.increaseUse", useType);
	}
	//조회(월간)
	public List<VisitUseDto> selectList() {
        return sqlSession.selectList("visit.selectUseList");
    }



}
