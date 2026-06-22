package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.vo.StreamerWithCkVO;

@Repository
public class StreamerWithDao {

	@Autowired
	private SqlSession sqlSession;
	
	//같이 ck한 사람 조회
	public List<StreamerWithCkVO> withCk(int streamerNo) {
	    return sqlSession.selectList("streamerWith.withCk", streamerNo);
	}
}
