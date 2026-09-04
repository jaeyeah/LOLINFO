package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.vo.SitemapVO;

@Repository
public class SitemapDao {

	@Autowired
	private SqlSession sqlSession;
	
    // 스트리머 상세 페이지 목록
    public List<SitemapVO> selectStreamerList() {
        return sqlSession.selectList("sitemap.selectStreamerList");
    }

    // 대회 상세 페이지 목록
    public List<SitemapVO> selectTournamentList() {
        return sqlSession.selectList("sitemap.selectTournamentList");
    }
	
}
