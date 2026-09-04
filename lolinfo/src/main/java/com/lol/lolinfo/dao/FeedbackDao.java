package com.lol.lolinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.FeedbackDto;

@Repository
public class FeedbackDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(FeedbackDto feedbackDto) {
		sqlSession.insert("feedback.insert",feedbackDto);
	}
	
	
}
