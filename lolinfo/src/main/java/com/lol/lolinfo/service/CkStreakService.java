package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.CkStreakDao;
import com.lol.lolinfo.dto.CkStreakDto;

@Service
public class CkStreakService {

    @Autowired
    private CkStreakDao ckStreakDao;


    public CkStreakDto selectOne(Integer streamerNo) {
        return ckStreakDao.selectOne(streamerNo);
    }


    public List<CkStreakDto> selectCurrentWinRanking() {
        return ckStreakDao.selectCurrentWinRanking();
    }


    public List<CkStreakDto> selectMaxWinRanking() {
        return ckStreakDao.selectMaxWinRanking();
    }


    @Transactional
    public void refresh(Integer streamerNo) {

        int count = ckStreakDao.countCk(streamerNo);

        // CK 기록이 전부 사라진 경우
        if (count == 0) {
            ckStreakDao.delete(streamerNo);
            return;
        }

        // 현재 / 최고 연승·연패 전체 재계산
        ckStreakDao.refresh(streamerNo);
    }


    @Transactional
    public void rebuildAll() {

        // 기존 파생 통계 초기화
        ckStreakDao.deleteAll();

        // CK를 한 경기 이상 진행한 스트리머 조회
        List<Integer> streamerNos =
                ckStreakDao.selectCkStreamerNos();

        // 전체 재생성
        for (Integer streamerNo : streamerNos) {
            ckStreakDao.refresh(streamerNo);
        }
    }
}