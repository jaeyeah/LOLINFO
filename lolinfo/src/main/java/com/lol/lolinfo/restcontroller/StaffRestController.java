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

import com.lol.lolinfo.dao.StaffDao;
import com.lol.lolinfo.dto.StaffDto;
import com.lol.lolinfo.vo.StaffListVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/staff")
public class StaffRestController {
	
	@Autowired
	private StaffDao staffDao;
	
	// 등록과 추가등록은 tournamentService에서 진행
	
	//목록조회 1
	@GetMapping("/streamer/{streamerNo}")
	public List<StaffListVO> selectListByStreamerNo(@PathVariable int streamerNo){
		return staffDao.selectListByStreamerNo(streamerNo);
	}

	//목록조회 2
//	@GetMapping("/tournament/{tournamentId}")
//	public List<HostListVO> selectListByTournamenId(@PathVariable int tournamentId){
//		return hostDao.selectListByTournamentId(tournamentId);
//	}
	
//	//삭제
	@DeleteMapping("/")
	public void delete(@RequestBody StaffDto staffDto) {
		staffDao.delete(staffDto);
		System.out.println("감독/코치 삭제 실행");
	}
}
