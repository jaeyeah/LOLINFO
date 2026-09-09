package com.lol.lolinfo.configuration;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfiguration {

	@Bean
	public CacheManager cacheManager() {
		
		// 1. 대회목록 캐시를 생성
		CaffeineCacheManager cacheManager =
                new CaffeineCacheManager("tournamentList");
		// 2. 최대 300개의 서로 다른 조회결과를 저장하며, 1시간 후 자동 만료
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .maximumSize(10)
                        .expireAfterWrite(Duration.ofHours(6))
        );

        return cacheManager;
	}
	
}
