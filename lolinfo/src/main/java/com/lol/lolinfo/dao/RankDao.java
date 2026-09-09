package com.lol.lolinfo.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.vo.CkRankingVO;
import com.lol.lolinfo.vo.CkStreakRankingVO;
import com.lol.lolinfo.vo.MyeolmangRankingVO;

@Repository
public class RankDao {

    @Autowired
    private SqlSession sqlSession;

    @Cacheable(value = "ckRanking",key = "'currentStreak:' + #limit")
    public List<CkStreakRankingVO> selectCurrentStreakRanking(int limit) {
        return sqlSession.selectList(
                "rank.selectCurrentStreakRanking",limit
        );
    }

    @Cacheable(value = "ckRanking",key = "'maxStreak:' + #limit")
    public List<CkStreakRankingVO> selectMaxStreakRanking(int limit) {
        return sqlSession.selectList(
                "rank.selectMaxStreakRanking",limit
        );
    }

    @Cacheable(value = "ckRanking",key = "'win:' + #period + ':' + #year + ':' + #limit")
    public List<CkRankingVO> selectWinRanking(
            String period,
            int year,
            int limit) {
        Map<String, Object> params = new HashMap<>();

        params.put("period", period);
        params.put("year", year);
        params.put("limit", limit);

        return sqlSession.selectList(
                "rank.selectWinRanking",params
        );
    }

    @Cacheable(value = "ckRanking",key = "'winRate:' + #minPlayCount + ':' + #limit")
    public List<CkRankingVO> selectWinRateRanking(
            int minPlayCount,
            int limit) {
        Map<String, Object> params = new HashMap<>();

        params.put("minPlayCount", minPlayCount);
        params.put("limit", limit);

        return sqlSession.selectList(
                "rank.selectWinRateRanking",
                params
        );
    }
    
    // 멸망전 랭킹
    public List<MyeolmangRankingVO> selectMyeolmangRanking(
            String period,
            int startYear,
            int limit) {

        Map<String, Object> params = new HashMap<>();

        params.put("period", period);
        params.put("startYear", startYear);
        params.put("limit", limit);

        return sqlSession.selectList(
                "rank.selectMyeolmangRanking",
                params
        );
    }
    
}