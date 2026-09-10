package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class MyeolmangRankingCacheService {

    @Autowired
    private CacheManager cacheManager;

    // 트랜잭션이 끝나기 전에 이전 DB 결과로 캐시가 다시 채워지는 것을 방지한다.
    public void evictAfterCommit() {
        if (TransactionSynchronizationManager.isActualTransactionActive()
                && TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    clear();
                }
            });
        }
        else {
            clear();
        }
    }

    private void clear() {
        cacheManager.getCache("myeolmangRanking").clear();
    }
}
