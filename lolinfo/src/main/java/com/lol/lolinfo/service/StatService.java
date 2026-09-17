package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.StatDao;
import com.lol.lolinfo.vo.stat.StatMonthlyVO;

@Service
public class StatService {

    @Autowired
    private StatDao statDao;

    @Transactional(readOnly = true)
    public StatMonthlyVO getMonthlyStat(int year) {

        List<StatMonthlyVO.Month> months =
            statDao.selectMonthlyStat(year);

        return StatMonthlyVO.builder()
            .year(year)
            .months(months)
            .build();
    }
}