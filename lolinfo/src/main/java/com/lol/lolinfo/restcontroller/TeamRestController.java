package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.TeamDao;
import com.lol.lolinfo.dto.TeamDto;
import com.lol.lolinfo.vo.StreamerTeamListVO;
import com.lol.lolinfo.vo.TeamListVO;
import com.lol.lolinfo.vo.TeamResponseVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/team")
public class TeamRestController {

	@Autowired
	private TeamDao teamDao;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody TeamDto teamDto) {
		teamDao.insert(teamDto);
		System.out.println("팀 등록 실행");
	}
	//등록시 스트리머 중복체크 -> id로 변경
	@GetMapping("/check/{streamerName}")
	public TeamResponseVO check(@PathVariable String streamerName) {
		//if(streamerName==null) return;
		return teamDao.checkAndConvert(streamerName);
	}
	
	
	
	@GetMapping("/{tournamentId}")
	public List<TeamListVO> selectList(@PathVariable int tournamentId){
		return teamDao.selectList(tournamentId);
	}

	//스트리머가 포함된 팀목록
	@GetMapping("/streamer/{streamerNo}")
	public List<StreamerTeamListVO> selectListByStreamerNo(@PathVariable int streamerNo){
		return teamDao.selectListByStreamerNo(streamerNo);
	}
	


	
}
