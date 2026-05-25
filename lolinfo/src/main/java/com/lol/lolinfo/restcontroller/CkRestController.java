package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dto.CkDto;
import com.lol.lolinfo.service.CkService;
import com.lol.lolinfo.service.TokenService;
import com.lol.lolinfo.vo.CkListVO;
import com.lol.lolinfo.vo.CkParticipantVO;
import com.lol.lolinfo.vo.CkVO;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.TokenVO;

@CrossOrigin
@RestController
@RequestMapping("/api/ck")
public class CkRestController {

	@Autowired
	private CkDao ckDao;
	@Autowired
	private CkService ckService;
	@Autowired
	private TokenService tokenService;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody CkVO ckVO,
			 @RequestHeader("Authorization") String bearerToken
			) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
		ckVO.setCkCreatedBy(tokenVO.getLoginId());
		ckService.insert(ckVO);
		System.out.println("CK 등록 실행");
	}
	
	// 조회 - 전체 CK (페이지네이션)
	@GetMapping("/")
	public PageResponseVO<CkDto> selectList(@RequestParam(defaultValue = "1") int page){
		int totalCount = ckDao.count();
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setTotalCount(totalCount);
		List<CkDto> list = ckDao.selectList(pageVO);
		return new PageResponseVO<>(list, pageVO);
	}
	
	// 조회 - CK별 참여 스트리머(모달)
	@GetMapping("/{ckId}/participant")
	public List<CkParticipantVO> selectParticipantList(@PathVariable int ckId){
		return ckDao.selectParticipantList(ckId);
	}
	
	// 상세조회 - 스트리머별 CK 목록
	@GetMapping("/streamer/{streamerNo}")
	public PageResponseVO<CkListVO> selectListByStreamer (
			@PathVariable int streamerNo, @RequestParam(defaultValue = "1") int page){
		return ckService.selectListByStreamer(streamerNo, page);
	}
	
	// 상세조회 - 스트리머별 맞라인 상대전적
//	@GetMapping("/{streamerNo}/vs")
//	public List<CkListVO> selectVsList (@PathVariable int streamerNo){
//		return ckDao.selectVsList(null)
//	}
	
	// 상세조회 - 스트리머별 포지션 전적
//	@GetMapping("/{streamerNo}/position")
//	public List<CkListVO> selectPosition (@PathVariable int streamerNo){
//		return ckDao.selectPosition(null)
//	}
	
	
}
