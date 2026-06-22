package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
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
	
	// 회원삭제(추방)
	@DeleteMapping("/members/{memberId}")
	public void delete(@PathVariable String memberId, 
			@RequestHeader("Authorization") String bearerToken) {
		memberService.delete(memberId, bearerToken);
	}
	
	// 회원 등급수정
	@PatchMapping("/members/{memberId}/memberLevel")
	public void chageLevel(@PathVariable String memberId,
							@RequestParam String memberLevel) {
		memberService.changeLevel(memberId,memberLevel);
	}
	
}
