package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.MemberDao;
import com.lol.lolinfo.dto.MemberDto;
import com.lol.lolinfo.error.TargetNotfoundException;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.TokenVO;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private TokenService tokenService;
	
	public PageResponseVO<MemberDto> selectList(int page, String type, String keyword){
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);

		if (type != "" && keyword != "") { // 검색일때
			int totalCount = memberDao.countSearchMember(type, keyword);
			pageVO.setTotalCount(totalCount);
			List<MemberDto> list = memberDao.selectSearchList(type, keyword, pageVO);
			return new PageResponseVO<>(list, pageVO);
		} else { // 검색이 아닐때
			int totalCount = memberDao.countMember();
			pageVO.setTotalCount(totalCount);
			List<MemberDto> list = memberDao.selectListWithPaging(pageVO);
			return new PageResponseVO<>(list, pageVO);
		}
	}
	
	public void delete(String memberId, String bearerToken) {
		//계정 삭제
		MemberDto memberDto = memberDao.selectOne(memberId);
		if(memberDto ==null) throw new TargetNotfoundException("존재하지 않는 회원입니다");
		memberDao.delete(memberId);
//		//토큰 삭제
//		TokenVO tokenVO = tokenService.parse(bearerToken);
//		memberTokenDao.deleteByTarget(tokenVO.getLoginId());
	}
	
	public void changeLevel(String memberId, String memberLevel) {
		MemberDto memberDto = memberDao.selectOne(memberId);
		if(memberDto == null) throw new TargetNotfoundException("존재하지 않는 회원입니다");
		memberDto.setMemberLevel(memberLevel);
		memberDao.updateMemberLevel(memberDto);
	}
	
	
}
