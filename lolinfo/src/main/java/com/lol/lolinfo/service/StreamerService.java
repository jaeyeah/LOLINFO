package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.StreamerWithDao;
import com.lol.lolinfo.dto.CkDto;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.StreamerWithCkVO;
import com.lol.lolinfo.vo.StreamerWithTournamentDetailVO;
import com.lol.lolinfo.vo.StreamerWithTournamentVO;

@Service
public class StreamerService {

	@Autowired
	private StreamerWithDao streamerWithDao;
	
	public PageResponseVO<StreamerWithCkVO> withCk(int streamerNo, int page, String keyword){
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setKeyword(String.valueOf(streamerNo));
		pageVO.setSearchKeyword(keyword);
		List<StreamerWithCkVO> list = streamerWithDao.withCk(pageVO);
		int totalCount = list.isEmpty() ? 0 : list.get(0).getTotalCount();
		pageVO.setTotalCount(totalCount);
		return new PageResponseVO<>(list, pageVO);
	}
	public PageResponseVO<StreamerWithTournamentVO> withTournament(int streamerNo, int page, String keyword){
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setKeyword(String.valueOf(streamerNo));
		pageVO.setSearchKeyword(keyword);
		List<StreamerWithTournamentVO> list = streamerWithDao.withTournament(pageVO);
		int totalCount = list.isEmpty() ? 0 : list.get(0).getTotalCount();
		pageVO.setTotalCount(totalCount);
		return new PageResponseVO<>(list, pageVO);
	}
	public List<StreamerWithTournamentDetailVO> withTournamentDetail(int streamerNo, int partnerNo){
		return streamerWithDao.withTournamentDetail(streamerNo, partnerNo);
	}
	
	
	
	
}
