package com.lol.lolinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.StaffDto;

@Repository
public class StaffDao {

	@Autowired
	private SqlSession sqlSession;
	
	public void insert(StaffDto staffDto) {
		sqlSession.insert("staff.insert", staffDto);
	}
}
