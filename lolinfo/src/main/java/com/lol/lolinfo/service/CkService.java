package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.vo.CkListVO;
import com.lol.lolinfo.vo.CkVO;
import com.lol.lolinfo.vo.CkVsVO;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;

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
		int totalCount = ckDao.countByStreamer(streamerNo);
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setTotalCount(totalCount);
		pageVO.setKeyword(String.valueOf(streamerNo));
		List<CkListVO> list = ckDao.selectListByStreamer(pageVO);
		return new PageResponseVO<>(list, pageVO);
	}
	
	public List<CkVsVO> selectVsList(int streamerNo){
		return ckDao.selectVsList(streamerNo);
	}

}
