package com.lol.lolinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.ScrimDto;

@Repository
public class ScrimDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(ScrimDto scrimDto) {
		sqlSession.insert("scrim.insert",scrimDto);
	}
}
