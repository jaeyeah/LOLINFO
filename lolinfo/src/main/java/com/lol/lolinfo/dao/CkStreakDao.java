package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.CkStreakDto;

@Repository
public class CkStreakDao {

	@Autowired
    private SqlSession sqlSession;

    public CkStreakDto selectOne(Integer streamerNo) {
        return sqlSession.selectOne("ckStreak.selectOne", streamerNo);
    }

    public List<CkStreakDto> selectList() {
        return sqlSession.selectList("ckStreak.selectList");
    }

    public List<Integer> selectCkStreamerNos() {
        return sqlSession.selectList("ckStreak.selectCkStreamerNos");
    }

    public int countCk(Integer streamerNo) {
        return sqlSession.selectOne("ckStreak.countCk", streamerNo);
    }

    public int refreshAll(List<Integer> streamerNos) {
        return sqlSession.update("ckStreak.refreshAll", streamerNos);
    }

    public int delete(Integer streamerNo) {
        return sqlSession.delete("ckStreak.delete", streamerNo);
    }
    public int deleteAll() {
        return sqlSession.delete("ckStreak.deleteAll");
    }
    public int deleteNoHistory(List<Integer> streamerNos) {
        return sqlSession.delete("ckStreak.deleteNoHistory",streamerNos);
    }
}
