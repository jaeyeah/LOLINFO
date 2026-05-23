package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.vo.CkVO;

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
	
}
