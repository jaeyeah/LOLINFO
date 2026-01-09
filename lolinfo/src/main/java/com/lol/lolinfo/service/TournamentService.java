package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.HostDao;
import com.lol.lolinfo.dao.TournamentDao;
import com.lol.lolinfo.dto.HostDto;
import com.lol.lolinfo.vo.TournamentRequestVO;

@Service
public class TournamentService {
	
	@Autowired
    private TournamentDao tournamentDao;
	@Autowired
    private HostDao hostDao;

    @Transactional
    public void insertTournament(TournamentRequestVO request) {
    	// 대회 등록
    	tournamentDao.insert(request.getTournamentDto());
    	// 대회 번호 추출
    	int tournamentId = request.getTournamentDto().getTournamentId();
    	// 개최자 등록
    	for(HostDto hostDto : request.getHostDto()) {
    		hostDto.setHostTournament(tournamentId);
    		if(hostDto.getHostStreamer() == null) continue; 
    		hostDao.insert(hostDto);		
    	}
    }

    @Transactional
    public void updateTournament(TournamentRequestVO request) {
    	// 대회 수정
    	tournamentDao.update(request.getTournamentDto());
    	int tournamentId = request.getTournamentDto().getTournamentId();
    	// 개최자 추가등록
    	for(HostDto hostDto : request.getHostDto()) {
    		hostDto.setHostTournament(tournamentId);
    		if(hostDto.getHostStreamer() == null) continue; 
    		hostDao.insert(hostDto);		
    	}
    }

}
