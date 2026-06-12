package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.VisitDao;
import com.lol.lolinfo.dto.VisitDto;

@Service
public class VisitService {

	@Autowired
	private VisitDao visitDao;
	
	public void insert(VisitDto visitDto) {
		if (visitDto.getVisitorId() == null || visitDto.getVisitorId().isBlank()) {
	        return;
	    }
		try {
			visitDao.insert(visitDto);
		}
		catch(Exception e) {
			System.out.println("방문 등록 실패");
			e.printStackTrace();
		}
	}
	
}
