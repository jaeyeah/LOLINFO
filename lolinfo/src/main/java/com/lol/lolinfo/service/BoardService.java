package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.BoardDao;
import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dto.BoardDto;
import com.lol.lolinfo.dto.CkScheduleDto;
import com.lol.lolinfo.error.NeedPermissionException;
import com.lol.lolinfo.error.TargetNotfoundException;
import com.lol.lolinfo.vo.TokenVO;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CkDao ckDao;
	
	
	//등록
	public void insert(BoardDto boardDto, CkScheduleDto scheduleDto,String bearerToken) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
		if(tokenVO == null) throw new TargetNotfoundException();
		boardDto.setBoardWriter(tokenVO.getLoginId());
		int boardId = boardDao.sequence();
		boardDto.setBoardId(boardId);
		boardDao.insert(boardDto);
		// CK 예정 정보가 전달된 경우
	    if(scheduleDto != null) {
	    	scheduleDto.setBoardId(boardId);
	        ckDao.insertSchedule(scheduleDto);
	    }
	}
	
	// 수정
    @Transactional
    public void edit(BoardDto boardDto,CkScheduleDto scheduleDto,String bearerToken) {
        int boardId = boardDto.getBoardId();
        requireManagePermission(boardId, bearerToken);
        // 기존 게시글 수정
        boardDao.update(boardDto);
        // 기존 CK 예정정보 확인
        CkScheduleDto originSchedule =ckDao.selectOneSchedule(boardId);

        if(scheduleDto != null) {
            scheduleDto.setBoardId(boardId);
            if(originSchedule == null) {
                ckDao.insertSchedule(scheduleDto);// 기존에는 일반 글이었는데 CK 일정이 추가된 경우
            }
            else {
                ckDao.updateSchedule(scheduleDto);// 기존 CK 일정 수정
            }
        }
        else {
            // CK 일정이 제거된 경우
            if(originSchedule != null) {ckDao.deleteSchedule(boardId);}
        }
    }
	//삭제
    @Transactional
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
