package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.FeedbackDto;
import com.lol.lolinfo.vo.FeedbackRequestVO;
import com.lol.lolinfo.vo.PageVO;

@Repository
public class FeedbackDao {

	@Autowired
	private SqlSession sqlSession;
	
	
	// 등록
	public void insert(FeedbackDto feedbackDto) {
		sqlSession.insert("feedback.insert",feedbackDto);
	}
	
	// 목록조회
	public List<FeedbackDto> selectList(PageVO pageVO){
		return sqlSession.selectList("feedback.selectList",pageVO);
	}
	public int count() {
		return sqlSession.selectOne("feedback.count");
	}
	
	//관리자 - 피드백상태 수정
	public void updateStatus(FeedbackRequestVO feedbackRequestVO) {
		sqlSession.update("feedback.updateStatus",feedbackRequestVO);
	}
	
	
}
