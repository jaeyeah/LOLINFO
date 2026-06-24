package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.MemberDao;
import com.lol.lolinfo.dto.MemberDto;
import com.lol.lolinfo.service.TokenService;
import com.lol.lolinfo.vo.TokenVO;

@CrossOrigin
@RestController
@RequestMapping("/api/mypage")
public class MyPageRestController {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private MemberDao memberDao;
	
	
	@GetMapping("/")
	public MemberDto selectMember(@RequestHeader("Authorization") String bearerToken){
		TokenVO tokenVO = tokenService.parse(bearerToken);
		String memberId = tokenVO.getLoginId();
		return memberDao.selectOne(memberId);
	}
	
}
