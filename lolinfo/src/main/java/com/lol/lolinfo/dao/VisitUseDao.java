package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.vo.VisitUseListVO;

@Repository
public class VisitUseDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록+추가(merge)
	public void increase(String useType) {
        sqlSession.update("visit.increaseUse", useType);
	}
	//조회(월간)
	public List<VisitUseListVO> selectMonth(String month) {
        return sqlSession.selectList("visit.selectUseMonth", month);
    }
	//조회(연간)
	public List<VisitUseListVO> selectYear(String year) {
        return sqlSession.selectList("visit.selectUseYear", year);
    }



}
