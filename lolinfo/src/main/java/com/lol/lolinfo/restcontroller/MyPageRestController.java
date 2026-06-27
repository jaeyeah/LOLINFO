package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dao.MemberDao;
import com.lol.lolinfo.dao.ScrimDao;
import com.lol.lolinfo.service.MemberService;
import com.lol.lolinfo.service.TokenService;
import com.lol.lolinfo.vo.CkMyPageVO;
import com.lol.lolinfo.vo.MemberVO;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.ScrimMyPageVO;
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
	@Autowired
	private ScrimDao scrimDao;
	@Autowired
	private CkDao ckDao;
	
	// 기본페이지
	@GetMapping("/")
	public MemberVO selectMember(@RequestHeader("Authorization") String bearerToken){
		TokenVO tokenVO = tokenService.parse(bearerToken);
		String memberId = tokenVO.getLoginId();
		return memberDao.selectMypage(memberId);
	}
	// 등록한 스크림 목록
	@GetMapping("/scrim")
	public PageResponseVO<ScrimMyPageVO> selectScrim(@RequestHeader("Authorization") String bearerToken, @RequestParam(defaultValue = "1") int page){
		TokenVO tokenVO = tokenService.parse(bearerToken);
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setKeyword(tokenVO.getLoginId());
		List<ScrimMyPageVO> list = scrimDao.selectMyPage(pageVO);
		int totalCount = list.isEmpty() ? 0 : list.get(0).getTotalCount();
		pageVO.setTotalCount(totalCount);
		return new PageResponseVO<>(list, pageVO);
	}
	
	// 등록한 CK 목록
	@GetMapping("/ck")
	public PageResponseVO<CkMyPageVO> selectCk(@RequestHeader("Authorization") String bearerToken, @RequestParam(defaultValue = "1") int page){
		TokenVO tokenVO = tokenService.parse(bearerToken);
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setKeyword(tokenVO.getLoginId());
		List<CkMyPageVO> list = ckDao.selectMyPage(pageVO);
		int totalCount = list.isEmpty() ? 0 : list.get(0).getTotalCount();
		pageVO.setTotalCount(totalCount);
		return new PageResponseVO<>(list, pageVO);
	}
	
	
	
	
	
	// 회원탈퇴
	@DeleteMapping("/")
	public void delete(@RequestHeader("Authorization") String bearerToken) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
		String memberId = tokenVO.getLoginId();
		memberService.delete(memberId, bearerToken);
	}
}
