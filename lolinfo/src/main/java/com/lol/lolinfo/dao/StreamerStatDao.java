package com.lol.lolinfo.dao;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import com.lol.lolinfo.vo.stat.StreamerMonthlyStatVO.*;

@Repository
public class StreamerStatDao {
    private final SqlSession sqlSession;
    public StreamerStatDao(SqlSession sqlSession) { this.sqlSession = sqlSession; }
    private Map<String, Object> params(int streamerNo, LocalDateTime start, LocalDateTime end) {
        return Map.of("streamerNo", streamerNo, "start", start, "end", end);
    }
    public List<PositionRow> selectMonths(int streamerNo, LocalDateTime start, LocalDateTime end) {
        return sqlSession.selectList("streamerStat.selectMonths", params(streamerNo, start, end));
    }
    public List<Opponent> selectOpponents(int streamerNo, LocalDateTime start, LocalDateTime end) {
        return sqlSession.selectList("streamerStat.selectOpponents", params(streamerNo, start, end));
    }
}
