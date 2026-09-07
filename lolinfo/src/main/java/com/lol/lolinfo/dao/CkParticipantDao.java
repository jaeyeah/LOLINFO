package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CkParticipantDao {

    @Autowired
    private SqlSession sqlSession;

    public List<Integer> selectStreamerNos(Integer ckId) {
        return sqlSession.selectList(
            "ck.selectStreamerNos",
            ckId
        );
    }
}