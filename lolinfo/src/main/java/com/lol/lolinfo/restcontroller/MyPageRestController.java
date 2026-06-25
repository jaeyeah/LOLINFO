package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.MemberDao;
import com.lol.lolinfo.service.MemberService;
import com.lol.lolinfo.service.TokenService;
import com.lol.lolinfo.vo.MemberVO;
import com.lol.lolinfo.vo.TokenVO;

@CrossOrigin
@RestController
@RequestMapping("/api/mypage")
public class MyPageRestController {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberService memberService;
	
	
	@GetMapping("/")
	public MemberVO selectMember(@RequestHeader("Authorization") String bearerToken){
		TokenVO tokenVO = tokenService.parse(bearerToken);
		String memberId = tokenVO.getLoginId();
		return memberDao.selectMypage(memberId);
	}
	
	@DeleteMapping("/")
	public void delete(@RequestHeader("Authorization") String bearerToken) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
		String memberId = tokenVO.getLoginId();
		memberService.delete(memberId, bearerToken);
	}
}
