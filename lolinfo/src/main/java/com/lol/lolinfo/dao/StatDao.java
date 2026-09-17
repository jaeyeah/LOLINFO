package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.vo.stat.StatMonthlyVO;

@Repository
public class StatDao {

    @Autowired
    private SqlSession sqlSession;

    public List<StatMonthlyVO.Month> selectMonthlyStat(int year) {
        return sqlSession.selectList("stat.selectMonthlyStat", year);
    }
}