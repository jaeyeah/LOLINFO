package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.TournamentDao;
import com.lol.lolinfo.dao.VisitUseDao;
import com.lol.lolinfo.dto.TournamentDto;
import com.lol.lolinfo.service.TournamentService;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.TournamentListVO;
import com.lol.lolinfo.vo.TournamentRequestVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/tournament")
public class TournamentRestController {

	@Autowired
	private TournamentDao tournamentDao;
	@Autowired
	private TournamentService tournamentService;
	@Autowired
	private VisitUseDao visitUseDao;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody TournamentRequestVO tournamentRequestVO) {
		tournamentService.insertTournament(tournamentRequestVO);
		System.out.println("대회 등록 실행");
	}
	
	
	//전체 조회 + 검색
	@GetMapping("/")
	public PageResponseVO<TournamentListVO> selectList(
				@RequestParam(defaultValue = "1") int page,
				@RequestParam(required=false) String keyword){
		// DB 중복조회를 방지하고자 totalCount를 한꺼번에 불러오는 것으로 수정
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setKeyword(keyword);
		
		List<TournamentListVO> list;
		if(keyword != null && !keyword.isBlank()) {
			list = tournamentService.searchList(pageVO);
		} else {
			list = tournamentService.selectList(pageVO);
		}
	    visitUseDao.increase("tournament_list");
	    return new PageResponseVO<>(list, pageVO);
	}

	//상세 조회
	@GetMapping("/{tournamentId}")
	public TournamentDto selectOne(@PathVariable int tournamentId) {
		visitUseDao.increase("tournament_detail");
		return tournamentDao.selectOne(tournamentId);
	}

	//수정
	@PutMapping("/{tournamentId}")
	public void update(@RequestBody TournamentRequestVO tournamentRequestVO) {
		tournamentService.updateTournament(tournamentRequestVO);
		System.out.println("대회 수정 실행");
	}
	//삭제

	
}
