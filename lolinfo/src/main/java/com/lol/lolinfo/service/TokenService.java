package com.lol.lolinfo.service;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.finalproject.dao.MemberTokenDao;
import com.kh.finalproject.dto.MemberTokenDto;
import com.lol.lolinfo.configuration.JwtProperties;
import com.lol.lolinfo.dto.MemberDto;
import com.lol.lolinfo.error.UnauthorizationException;
import com.lol.lolinfo.vo.TokenVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

	@Autowired
	private JwtProperties  jwtProperties;
	@Autowired
	private MemberTokenDao memberTokenDao;
	
	/// AccessToken 생성 ////////////////////////////////////////////
	public String generateAccessToken(MemberDto memberDto) {
		String keyStr = jwtProperties.getKeyStr();
		SecretKey key = Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
	
		// 만료시간 설정
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime(); // 현재시각
		calendar.add(Calendar.MINUTE, jwtProperties.getExpiration());
		Date expire = calendar.getTime(); // 만료시각
		
		//Jwt 토큰 생성
		return Jwts.builder()
				.signWith(key)
				.expiration(expire)
				.issuedAt(now)
				.issuer(jwtProperties.getIssuer()) // 발행자
				.claim("loginId", memberDto.getMemberId())
				.claim("loginLevel", memberDto.getMemberLevel())
				.claim("loginNickname", memberDto.getMemberNickname())
				//.claim("loginPoint", memberDto.getMemberPoint())
			.compact();
	}
	public String generateAccessToken(TokenVO tokenVO) {
		return generateAccessToken(MemberDto.builder()
				.memberId(tokenVO.getLoginId())
				.memberLevel(tokenVO.getLoginLevel())
				.build());
	}
	

	/// RefreshToken 생성 ////////////////////////////////////////////
	public String generateRefreshToken(MemberDto memberDto) {
		String keyStr = jwtProperties.getKeyStr();
		SecretKey key = Keys.hmacShaKeyFor(keyStr.getBytes(StandardCharsets.UTF_8));
	
		// 만료시간 설정
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.add(Calendar.MINUTE, jwtProperties.getRefreshExpiration());
		Date expire = calendar.getTime(); // 만료시각
		
		//Jwt 토큰 생성
		String token = Jwts.builder()
				.signWith(key)
				.expiration(expire)
				.issuedAt(now)
				.issuer(jwtProperties.getIssuer()) // 발행자
				.claim("loginId", memberDto.getMemberId())
				.claim("loginLevel", memberDto.getMemberLevel())
				.claim("loginNickname", memberDto.getMemberNickname())
				//.claim("loginPoint", memberDto.getMemberPoint())
			.compact();
		
		// 같은 아이디로 저장된 발행 내역을 모두 삭제
		memberTokenDao.deleteByTarget(memberDto.getMemberId());
		// DB 저장 (액세스 토큰과 달라지는 작업)
		memberTokenDao.insert(MemberTokenDto.builder()
				.memberTokenTarget(memberDto.getMemberId())
				.memberTokenValue(token)
			.build());
		
		return token;
	}
	
	public String generateRefreshToken(TokenVO tokenVO) {
		return generateAccessToken(MemberDto.builder()
				.memberId(tokenVO.getLoginId())
				.memberLevel(tokenVO.getLoginLevel())
				.memberNickname(tokenVO.getLoginNickname())
				.build());
	}
	
	/// 넘어온 정보를 파싱(해석해서 변환) ////////////////////////////////////////////
	public TokenVO parse(String authorization) {
		//Bearer 토큰 검증
		if(authorization.startsWith("Bearer ") == false) 
			throw new UnauthorizationException();
		//앞글자 제거(Bearer)
		String token = authorization.substring(7);
	
		SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getKeyStr().getBytes(StandardCharsets.UTF_8));
		Claims claims = (Claims) 	Jwts.parser()
								.verifyWith(key)
								.requireIssuer(jwtProperties.getIssuer())
							.build()
								.parse(token)
								.getPayload();
		//Claims에 담긴 데이터를 TokenVO로 변환 -> 반환
		return TokenVO.builder()
								.loginId((String) claims.get("loginId"))
								.loginLevel((String) claims.get("loginLevel"))
							.build();
	}
	
	
	/// 토큰 만료까지 남은 시간 구하기 ////////////////////////////////////////////
	public long getRemain(String authorization) {
		//Bearer 토큰 검증
		if(authorization.startsWith("Bearer ") == false) 
			throw new UnauthorizationException();
		//앞글자 제거(Bearer)
		String token = authorization.substring(7);
	
		SecretKey key = Keys.hmacShaKeyFor(jwtProperties.getKeyStr().getBytes(StandardCharsets.UTF_8));
		Claims claims = (Claims) 	Jwts.parser()
								.verifyWith(key)
								.requireIssuer(jwtProperties.getIssuer())
							.build()
								.parse(token)
								.getPayload();
		// 시간추출 (getTime() = Date객체 -> 숫자(ms) 변환)
		Date expire = claims.getExpiration();
		Date now = new Date();
		return expire.getTime() - now.getTime(); // 만료시각 - 현재시각
	}
	
	////// checkRefresh Token
	public boolean checkRefreshToken(TokenVO tokenVO, String refreshToken) {
		MemberTokenDto memberTokenDto = memberTokenDao.selectOne(
				MemberTokenDto.builder()
					.memberTokenTarget(tokenVO.getLoginId())
					.memberTokenValue(refreshToken.substring(7))
				.build()
				);
		// 결과 없을때
		if(memberTokenDto == null) return false;
		memberTokenDao.deleteByTarget(tokenVO.getLoginId());
		//결과 있음
		return true;
	}
	
	
}
