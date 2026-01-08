package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.HostDao;
import com.lol.lolinfo.dto.HostDto;
import com.lol.lolinfo.vo.HostListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/host")
public class HostRestController {
	
	@Autowired
	private HostDao hostDao;
	
	// 등록과 추가등록은 tournamentService에서 진행
	
	//목록조회 1
	@GetMapping("/tournament/{tournamentId}")
	public List<HostListVO> selectListByTournamenId(@PathVariable int tournamentId){
		return hostDao.selectListByTournamentId(tournamentId);
	}
	//목록조회 2
	@GetMapping("/streamer/{streamerNo}")
	public List<HostListVO> selectListByStreamerNo(@PathVariable int streamerNo){
		return hostDao.selectListByStreamerNo(streamerNo);
	}
	
	//삭제
	@DeleteMapping("/")
	public void delete(@RequestBody HostDto hostDto) {
		hostDao.delete(hostDto);
		System.out.println("개최자 삭제 실행");
	}
}
