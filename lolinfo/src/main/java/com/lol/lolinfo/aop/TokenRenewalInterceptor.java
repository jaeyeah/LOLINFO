package com.lol.lolinfo.aop;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.finalproject.vo.TokenVO;
import com.lol.lolinfo.configuration.JwtProperties;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@Service
public class TokenRenewalInterceptor implements HandlerInterceptor{

	@Autowired
	private JwtProperties jwtProperties;
	@Autowired
	private TokenService tokenService;
	
	public boolean preHandle(HttpServletRequest request,
										HttpServletResponse response, Object handler) throws Exception {
		// 1) options 요청인 경우 -> 적용X(true)
		if(request.getMethod().equalsIgnoreCase("options")) return true;
		
		// 2) (비회원) Authorization 헤더가 없는 경우 -> 적용X(true)
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken == null) return true;
		
		
		System.out.println("1,2통과");
		// 3) 토큰의 남은 시간이 충분한 경우
		try { // Plan A
			long ms = tokenService.getRemain(bearerToken);
			if(ms >= jwtProperties.getRenewalLimit()*60L*1000L) return true;
			// 통과하지 못했다면 잔여시간이 촉박 → 토큰 재발급
			TokenVO tokenVO = tokenService.parse(bearerToken);
			String newAccessToken = tokenService.generateAccessToken(tokenVO);
			// 발급한 토큰을 클라이언트(응답 헤더)에게 전송
	        response.setHeader("Access-Control-Expose-Headers", "Access-Token"); 
	        response.setHeader("Access-Token", newAccessToken); 
	        return true;
		}
		catch(ExpiredJwtException e) {
			// 토큰 만료시
			response.setStatus(401);
			response.setContentType("application/json; charset=UTF-8");
			Map<String, String> body = new HashMap<>();
				body.put("status", "401");
				body.put("message", "TOKEN_EXPIRED");
			ObjectMapper mapper = new ObjectMapper(); // JSON 수동 생성기
			String json = mapper.writeValueAsString(body); // JSON 생성
			response.getWriter().write(json);
			return false;
		}
	}
}
