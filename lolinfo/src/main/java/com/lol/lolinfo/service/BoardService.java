package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.BoardDao;
import com.lol.lolinfo.dto.BoardDto;
import com.lol.lolinfo.error.TargetNotfoundException;
import com.lol.lolinfo.vo.TokenVO;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	@Autowired
	private TokenService tokenService;
	
	
	//등록
	public void insert(BoardDto boardDto, String bearerToken) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
		if(tokenVO == null) throw new TargetNotfoundException();
		boardDto.setBoardWriter(tokenVO.getLoginId());
		int boardId = boardDao.sequence();
		boardDto.setBoardId(boardId);
		boardDao.insert(boardDto);
	}
}
