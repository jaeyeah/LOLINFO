package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dto.CkDto;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.error.TargetNotfoundException;
import com.lol.lolinfo.vo.CkListVO;
import com.lol.lolinfo.vo.CkPeriodVO;
import com.lol.lolinfo.vo.CkVO;
import com.lol.lolinfo.vo.CkVsVO;
import com.lol.lolinfo.vo.PageResponseVO;

@Service
public class CkService {

	@Autowired
	private CkDao ckDao;
	
	@Transactional
	//CK+참여자 등록
	public void insert(CkVO ckVO) {
		int ckId = ckDao.sequence();
		ckVO.setCkId(ckId);
		ckDao.insert(ckVO); // CK등록
		if(ckVO.getParticipants() != null) {
			for(CkParticipantDto participant : ckVO.getParticipants()) {
				participant.setCkId(ckId);
				ckDao.insertParticipant(participant);
			}
		}
	}
	
	public PageResponseVO<CkListVO> selectListByStreamer(int streamerNo, int page){
		return selectListByStreamer(new CkPeriodVO(streamerNo, page, null, null));
	}

	public PageResponseVO<CkListVO> selectListByStreamer(CkPeriodVO query){
		query.setTotalCount(ckDao.countByStreamer(query));
		query.setPage(Math.min(query.getPage(), Math.max(1, query.getTotalPage())));
		List<CkListVO> list = ckDao.selectListByStreamer(query);
		return new PageResponseVO<>(list, query);
	}
	
	public List<CkVsVO> selectVsList(int streamerNo){
		return selectVsList(new CkPeriodVO(streamerNo, 1, null, null));
	}

	public List<CkVsVO> selectVsList(CkPeriodVO query){
		return ckDao.selectVsList(query);
	}
	
	// Patch - 날짜와 메모(CK이름)만 수정
	public void updateUnit(int ckId, CkDto ckDto) {
		CkDto originDto = ckDao.selectOne(ckId);
		if(originDto == null) throw new TargetNotfoundException();
		// 개별 항목 확인
		if(ckDto.getCkDate() != null) {
			originDto.setCkDate(ckDto.getCkDate());
		}
		if(ckDto.getCkMemo() != null) {
			originDto.setCkMemo(ckDto.getCkMemo());
		}
		if (ckDto.getCkWinner() != null) {
	        originDto.setCkWinner(ckDto.getCkWinner());
	    }
		ckDao.updateUnit(originDto);
	}

}
