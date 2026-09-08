package com.lol.lolinfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.vo.CkRankingVO;
import com.lol.lolinfo.vo.CkStreakRankingVO;

@Repository
public class CkRankDao {

    @Autowired
    private SqlSession sqlSession;


    public List<CkStreakRankingVO> selectCurrentStreakRanking(int limit) {

        return sqlSession.selectList(
                "ckRank.selectCurrentStreakRanking",
                limit
        );
    }


    public List<CkStreakRankingVO> selectMaxStreakRanking(int limit) {

        return sqlSession.selectList(
                "ckRank.selectMaxStreakRanking",
                limit
        );
    }


    public List<CkRankingVO> selectWinRanking(
            String period,
            int year,
            int limit) {

        Map<String, Object> params = new HashMap<>();

        params.put("period", period);
        params.put("year", year);
        params.put("limit", limit);

        return sqlSession.selectList(
                "ckRank.selectWinRanking",
                params
        );
    }


    public List<CkRankingVO> selectWinRateRanking(
            int minPlayCount,
            int limit) {

        Map<String, Object> params = new HashMap<>();

        params.put("minPlayCount", minPlayCount);
        params.put("limit", limit);

        return sqlSession.selectList(
                "ckRank.selectWinRateRanking",
                params
        );
    }
}