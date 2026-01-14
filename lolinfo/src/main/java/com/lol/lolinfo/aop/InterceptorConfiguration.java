package com.lol.lolinfo.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenRenewalInterceptor tokenRenewalInterceptor;
    @Autowired
    private MemberInterceptor memberInterceptor;
    @Autowired
    private TokenParsingInterceptor tokenParsingInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
    	registry.addInterceptor(tokenParsingInterceptor)
    	.addPathPatterns(
//    			"/board/**",
//    			"/board/reply/**",
//    			"/board/report/**",
    			"/board/viewUpdate/**")
//    	.excludePathPatterns(
//                "/board/",           // 게시글 전체 조회
//                "/board/page/**",    // 게시글 페이지 조회
//                "/board/contentsId/**", // 컨텐츠별 게시글 조회
//                "/board/{boardNo}"   // 게시글 상세 조회 (숫자만 오는 경우)
//        )
    	;

    	
        // 1. 로그인 검사 인터셉터 (회원 전용 기능 보호)
         registry.addInterceptor(memberInterceptor)
            .addPathPatterns(
                "/member/logout",      // 로그아웃
                "/point/**",
                "/content/**",
                "/quiz/**",
                "/admin/**",
                "/heart/**",
                "/review/report/**",
                "/board/**",
    			"/reply/**",
    			"/board/report/**"

                // contents 북마크 기능만 추가 나머지 컨텐츠 관련 부분 로그인 없이 허용                
                // 포인트 관련 전체 (/point/history 등)
                // "/point/store/**"   // 위 /point/** 가 이미 포함하므로 생략 가능
            )
            .excludePathPatterns(
                "/point/store/",       // ★ 상품 목록 조회는 로그인 없이 허용
                "/ranking/**",
                "/quiz/log/list/ranking/**",
//                "/board/",           // 게시글 전체 조회
                "/board/page/**",    // 게시글 페이지 조회
                "/board/contentsId/**", // 컨텐츠별 게시글 조회
                "/board/detail/{boardNo}",   // 게시글 상세 조회 (숫자만 오는 경우)
                "/reply/"
            	//"/review/list/**"	
            );
        
        // 2. 토큰 재발급 인터셉터 (로그인 연장)
        registry.addInterceptor(tokenRenewalInterceptor)
            .addPathPatterns("/**")    // 모든 요청에 대해 토큰 검사 시도
            .excludePathPatterns(
                "/member/join",
                "/member/login",
                "/member/logout",
                "/point/store/",       // 상품 목록도 제외
                "/member/refresh"
            ).order(2);
    }
}