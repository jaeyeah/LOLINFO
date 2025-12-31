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

import com.lol.lolinfo.dao.TournamentDao;
import com.lol.lolinfo.dto.StreamerDto;
import com.lol.lolinfo.dto.TournamentDto;
import com.lol.lolinfo.vo.StreamerListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/tournament")
public class TournamentRestController {

	@Autowired
	private TournamentDao tournamentDao;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody TournamentDto tournamentDto) {
		tournamentDao.insert(tournamentDto);
		System.out.println("대회 등록 실행");
	}
	
	// 등록시 check -> 스트리머 이름 조회해서 중복확인 + streamerNo 같이 반환
	
	
	
	//전체 조회
	@GetMapping("/")
	public List<TournamentDto> selectList(){
		return tournamentDao.selectList();
	}

	//상세 조회
	@GetMapping("/{tournamentId}")
	public TournamentDto selectOne(@PathVariable int tournamentId) {
		return tournamentDao.selectOne(tournamentId);
	}

	//수정
	
	//삭제

	
}
