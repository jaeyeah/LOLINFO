package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dto.MemberDto;
import com.lol.lolinfo.service.MemberService;
import com.lol.lolinfo.vo.PageResponseVO;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private MemberService memberService;
	
	//회원목록 조회
	@GetMapping("/members")
	public PageResponseVO<MemberDto> selectList(@RequestParam(defaultValue = "1") int page,
			@RequestParam(required = false) String type, 
			@RequestParam(required = false) String keyword) {
		return memberService.selectList(page, type, keyword);
	}
}
