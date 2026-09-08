package com.lol.lolinfo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.CkStreakDao;

@Service
public class CkStreakService {

    @Autowired
    private CkStreakDao ckStreakDao;


    @Transactional
    public void refreshAll(Collection<Integer> streamerNos) {
        if (streamerNos == null || streamerNos.isEmpty()) {
            return;
        }
        ckStreakDao.refreshAll(new ArrayList<>(streamerNos));
    }


    @Transactional
    public void rebuildAll() {

        // 기존 파생 통계 초기화
        ckStreakDao.deleteAll();

        // CK를 한 경기 이상 진행한 스트리머 조회
        List<Integer> streamerNos =
                ckStreakDao.selectCkStreamerNos();

        // 전체 재생성
        if (streamerNos != null && !streamerNos.isEmpty()) {
            ckStreakDao.refreshAll(streamerNos);
        }
    }
}