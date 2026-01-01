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
import com.lol.lolinfo.vo.TeamListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/team")
public class TeamRestController {

	@Autowired
	private TeamDao teamDao;
	
	@PostMapping("/")
	public void insert(@RequestBody TeamDto teamDto) {
		teamDao.insert(teamDto);
		System.out.println("팀 등록 실행");
	}
	
	@GetMapping("/{tournamentId}")
	public List<TeamListVO> selectList(@PathVariable int tournamentId){
		return teamDao.selectList(tournamentId);
	}
}
