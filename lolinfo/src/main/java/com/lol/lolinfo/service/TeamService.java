package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.StaffDao;
import com.lol.lolinfo.dao.TeamDao;
import com.lol.lolinfo.dto.StaffDto;
import com.lol.lolinfo.vo.TeamRequestVO;

@Service
public class TeamService {
	
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private StaffDao staffDao;

    @Transactional
    public void insertTeam(TeamRequestVO request) {
    	// 팀 등록
    	teamDao.insert(request.getTeamDto());
    	// 팀 번호 추출
    	int teamId = request.getTeamDto().getTeamId();
    	// 팀 등록
    	for(StaffDto staffDto : request.getStaffDto()) {
    		staffDto.setStaffTeam(teamId);
    		if(staffDto.getStaffStreamer() == null) continue;
    		staffDao.insert(staffDto);
    	}
    }


}
