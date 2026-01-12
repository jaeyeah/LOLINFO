package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	@PostMapping("/")
	public void insert(@RequestBody MemberDto memberDto) {
		memberDao.insert(memberDto);
	}
	
}
