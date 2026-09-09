package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.BoardDao;
import com.lol.lolinfo.dto.BoardDto;
import com.lol.lolinfo.dto.CkDto;
import com.lol.lolinfo.error.NeedPermissionException;
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
	
	//수정
	public void edit(BoardDto boardDto, String bearerToken) {
		int boardId = boardDto.getBoardId();
		requireManagePermission(boardId, bearerToken);
		boardDao.update(boardDto);
	}
	//삭제
	public void delete(int boardId, String bearerToken) {
		requireManagePermission(boardId, bearerToken);
		boardDao.delete(boardId);
	}	
	
	///---------------
	// 수정·삭제 권한 검사(작성자 or 관리자)
	private boolean requireManagePermission(int boardId, String bearerToken) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
		if(tokenVO == null) throw new TargetNotfoundException();
	    BoardDto originDto = boardDao.selectOne(boardId);
	    if (originDto == null) throw new TargetNotfoundException();
	    // 작성자 또는 관리자 여부 확인
	    boolean isOwner = tokenVO.getLoginId()
	            .equals(originDto.getBoardWriter());
	    boolean isAdmin = "관리자"
	            .equals(tokenVO.getLoginLevel());
	    if (!isOwner && !isAdmin) throw new NeedPermissionException();
	    return true;
	}
	
}
