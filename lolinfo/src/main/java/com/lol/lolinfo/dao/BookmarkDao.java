package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.BookmarkDto;
import com.lol.lolinfo.vo.BookmarkStreamerVO;

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
	
	//스트리머 즐겨찾기 목록 
	public List<BookmarkStreamerVO> selectStreamerList(String memberId){
		return sqlSession.selectList("bookmark.selectStreamerList",memberId);
	}
	
}
