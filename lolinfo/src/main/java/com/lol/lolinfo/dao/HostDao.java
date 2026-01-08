package com.lol.lolinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.HostDto;

@Repository
public class HostDao {

	@Autowired
	private SqlSession sqlSession;
	
	//개최자 등록
	public void insert(HostDto hostDto) {
		sqlSession.insert("host.insert",hostDto);
	}
	
}
