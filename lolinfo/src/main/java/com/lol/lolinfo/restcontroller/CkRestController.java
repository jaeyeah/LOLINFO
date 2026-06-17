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
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.VisitUseDao;
import com.lol.lolinfo.service.CkService;
import com.lol.lolinfo.service.TokenService;
import com.lol.lolinfo.vo.CkVO;
import com.lol.lolinfo.vo.TokenVO;

@CrossOrigin
@RestController
@RequestMapping("/api/ck")
public class CkRestController {

	@Autowired
	private CkService ckService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private VisitUseDao visitUseDao;
	
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
	
	// 조회 - 스트리머별 CK
	@GetMapping("/streamer/{streamerNo}")
	public List<CkVO> selectList(@PathVariable int streamerNo){
		System.out.println("스트리머 참여 CK 목록 조회");
		visitUseDao.increase("streamer_ck");
		return ckService.selectStreamer(streamerNo);	
	}
	
}
