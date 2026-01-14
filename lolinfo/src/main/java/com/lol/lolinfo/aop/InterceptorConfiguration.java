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

    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
            	
        // 1. 로그인 검사 인터셉터 (회원 전용 기능 보호)
         registry.addInterceptor(memberInterceptor)
            .addPathPatterns(
                "/member/logout",      // 로그아웃
                "/admin/**"
            )
            .excludePathPatterns(
            		
            );
        
        // 2. 토큰 재발급 인터셉터 (로그인 연장)
        registry.addInterceptor(tokenRenewalInterceptor)
            .addPathPatterns("/**")    // 모든 요청에 대해 토큰 검사 시도
            .excludePathPatterns(
                "/member/join",
                "/member/login",
                "/member/logout",
                "/member/refresh"
            ).order(2);
    }
}