package com.lol.lolinfo.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.finalproject.error.UnauthorizationException;
import com.lol.lolinfo.vo.TokenVO;

import io.jsonwebtoken.ExpiredJwtException; // ★ 이 임포트 추가 필수!
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class MemberInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getMethod().equalsIgnoreCase("options")) return true;

		// 2) Authorization 헤더 검사

		try {
			String bearerToken = request.getHeader("Authorization");
			if(bearerToken == null) throw new UnauthorizationException();
			
			// 토큰 해석
			TokenVO tokenVO = tokenService.parse(bearerToken);
			
			request.setAttribute("tokenVO", tokenVO);
			
			// 정보 저장
			request.setAttribute("loginId", tokenVO.getLoginId()); 
			request.setAttribute("loginLevel", tokenVO.getLoginLevel()); 
			return true;
		}
		catch (ExpiredJwtException e) {
			// ★ [추가] 토큰 만료 에러는 스택트레이스(긴 로그)를 찍지 않고 401만 보냄
			System.out.println(">>> [MemberInterceptor] 토큰 만료됨 (재로그인 필요)");
			response.sendError(401, "Token Expired");
			return false;
		}
		catch(Exception e){
			// 그 외 다른 에러는 로그 출력
			e.printStackTrace();
			response.sendError(401);
			return false;
		}
	}
}