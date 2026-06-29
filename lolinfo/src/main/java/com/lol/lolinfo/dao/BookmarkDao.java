package com.lol.lolinfo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.BookmarkDto;

@Repository
public class BookmarkDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(BookmarkDto bookmarkDto) {
		sqlSession.insert("bookmark.insert",bookmarkDto);
	}
	// 삭제
	public boolean delete(BookmarkDto bookmarkDto) {
		return sqlSession.delete("bookmark.delete",bookmarkDto) > 0;
	}
	
}
