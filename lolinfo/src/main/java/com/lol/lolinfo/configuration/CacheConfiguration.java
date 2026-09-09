package com.lol.lolinfo.configuration;

import java.time.Duration;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfiguration {

	@Bean
    public CacheManager cacheManager() {

        SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(
                List.of(
                		///           캐시이름         , 캐시용량 ,  지속시간
                        
                		//목록
                		createCache("tournamentList", 10, Duration.ofHours(6)),
                        createCache("streamerList", 10, Duration.ofHours(6)),
                        createCache("streamerSearch", 100, Duration.ofHours(1)),
                        
                        // CK
                        createCache("ckRanking", 50, Duration.ofMinutes(30)),
                        createCache("ckMonthlyRanking", 20, Duration.ofMinutes(30)),
                        createCache("ckMonthlyCount", 10, Duration.ofHours(1)),

                        // 메인
                        createCache("homeStats", 1, Duration.ofMinutes(1)),

                        // 스트리머 상세
                        createCache("streamerDetail", 300, Duration.ofMinutes(30))
                        
                )
        );

        return cacheManager;
    }
	/// -----------------------------------------------------------------------
 	/// 캐시만드는 시스템 구조화----------------------------------------------------
    private CaffeineCache createCache(
            String name,long maximumSize,Duration duration) {
        return new CaffeineCache(
                name,
                Caffeine.newBuilder()
                        .maximumSize(maximumSize)
                        .expireAfterWrite(duration)
                        .build()
        );
    }
}