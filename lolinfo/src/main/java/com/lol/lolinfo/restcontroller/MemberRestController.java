package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.MemberDao;
import com.lol.lolinfo.dto.MemberDto;

@CrossOrigin
@RestController
@RequestMapping("/api/member")
public class MemberRestController {

	@Autowired
	private MemberDao memberDao;

	//회원가입
	@PostMapping("/")
	public void insert(@RequestBody MemberDto memberDto) {
		memberDao.insert(memberDto);
	}
	// 회원가입 - 아이디 중복검사
	@GetMapping("/memberId/{memberId}")
	public boolean selectOne(@PathVariable String memberId) {
		MemberDto memberDto = memberDao.selectOne(memberId);
		return memberDto == null;
	}
	// 회원가입 - 닉네임 중복검사
	@GetMapping("/memberNickname/{memberNickname}")
	public boolean checkMemberNickname(@PathVariable String memberNickname) {
		MemberDto memberDto = memberDao.selectOneByMemberNickname(memberNickname);
		return memberDto == null;
	}
	
	//회원 조회
	@GetMapping("/")
	public List<MemberDto> selectList(){
		return memberDao.selectList();
	}
}
