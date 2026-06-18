package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.ScrimDao;
import com.lol.lolinfo.dto.ScrimDto;
import com.lol.lolinfo.error.TargetNotfoundException;
import com.lol.lolinfo.service.TokenService;
import com.lol.lolinfo.vo.ScrimListVO;
import com.lol.lolinfo.vo.ScrimRecordVO;
import com.lol.lolinfo.vo.ScrimVsRecordVO;
import com.lol.lolinfo.vo.TokenVO;

@CrossOrigin
@RestController
@RequestMapping("/api/scrim")
public class ScrimRestController {

	@Autowired
	private ScrimDao scrimDao;
	@Autowired
	private TokenService tokenService;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody ScrimDto scrimDto,
			@RequestHeader("Authorization") String bearerToken
			) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
		if(tokenVO == null) throw new TargetNotfoundException();
		scrimDto.setScrimCreatedBy(tokenVO.getLoginId());
		scrimDao.insert(scrimDto);
	}
	
	//특정 대회의 스크림 목록 조회
	@GetMapping("/{scrimTournament}")
	public List<ScrimListVO> selectList(@PathVariable int scrimTournament){
		return scrimDao.selectList(scrimTournament);
	}
	// 특정 대회의 팀별 스크림 승률 조회
	@GetMapping("/record/{scrimTournament}")
	public List<ScrimRecordVO> selectRecordList(@PathVariable int scrimTournament){
		return scrimDao.selectRecordList(scrimTournament);
	}
	// 팀별 상대전적
	@GetMapping("/{scrimTournament}/{scrimTeam}")
	public List<ScrimVsRecordVO> selectVsRecordList(
	        @PathVariable int scrimTournament,
	        @PathVariable int scrimTeam
	) {
	    return scrimDao.selectVsList(scrimTournament, scrimTeam);
	}
	
	
	//부분수정
	@PatchMapping("/")
	public void updateUnit(
	        @RequestBody ScrimDto scrimDto) {
	    scrimDao.updateUnit(scrimDto);
	}
	//삭제
	@DeleteMapping("/{scrimId}")
	public void delete(@PathVariable int scrimId) {
		scrimDao.delete(scrimId);
	}
	
	
	
}
