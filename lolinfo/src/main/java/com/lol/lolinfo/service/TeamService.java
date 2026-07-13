package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.StaffDao;
import com.lol.lolinfo.dao.StreamerDao;
import com.lol.lolinfo.dao.TeamDao;
import com.lol.lolinfo.dto.StaffDto;
import com.lol.lolinfo.vo.StreamerTierVO;
import com.lol.lolinfo.vo.TeamListVO;
import com.lol.lolinfo.vo.TeamRequestVO;

@Service
public class TeamService {
	
	@Autowired
	private TeamDao teamDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private StreamerDao streamerDao;

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

    @Transactional
    public void updateTeam(TeamRequestVO request) { 
    	// 팀 수정
    	teamDao.update(request.getTeamDto());
    	int teamId = request.getTeamDto().getTeamId();
    	// 스태프 추가등록
    	for(StaffDto staffDto : request.getStaffDto()) {
    		staffDto.setStaffTeam(teamId);
    		if(staffDto.getStaffStreamer() == null) continue;
    		staffDao.insert(staffDto);
    	}
    }
    
    
    @Transactional
    public List<TeamListVO> selectList(int tournamentId){
    	//1. 대회 팀 목록 조회
    	List<TeamListVO> teamList =  teamDao.selectList(tournamentId);
    	//2. 대회 티어 조회
    	List<StreamerTierVO> tierList = streamerDao.selectTierList(tournamentId);
    	//3. 각 팀 선수에게 포지션별 티어 적용
    	 for (TeamListVO team : teamList) {
             team.setTopTier(findTier(tierList,team.getTeamTop(),"TOP"));
             team.setJugTier(findTier(tierList,team.getTeamJug(),"JUG"));
             team.setMidTier(findTier( tierList,team.getTeamMid(),"MID"));
             team.setAdTier(findTier(tierList,team.getTeamAd(),"AD"));
             team.setSupTier(findTier(tierList,team.getTeamSup(),"SUP"));
         }
         return teamList;
     }

     private String findTier(List<StreamerTierVO> tierList,
             				Integer streamerNo, String position) {
         if (streamerNo == null || streamerNo == 0) return null;
         return tierList.stream()
                 .filter(tier ->
                         tier.getStreamerNo() == streamerNo
                         && position.equals(tier.getTierPosition())
                 )
                 .map(StreamerTierVO::getTierName)
                 .findFirst()
                 .orElse(null);
     }
 
    
}
