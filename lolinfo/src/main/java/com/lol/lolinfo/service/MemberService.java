package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.MemberDao;
import com.lol.lolinfo.dto.MemberDto;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;

@Service
public class MemberService {

	@Autowired
	private MemberDao memberDao;
	
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
}
