package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.VisitDao;
import com.lol.lolinfo.dto.VisitDto;
import com.lol.lolinfo.service.VisitService;
import com.lol.lolinfo.vo.VisitListVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/visit")
@Slf4j
@CrossOrigin
public class VisitRestController {

	@Autowired
	private VisitService visitService;
	@Autowired
	private VisitDao visitDao;
	
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody VisitDto visitDto) {
		visitService.insert(visitDto);
	}
	
	//로그인시 변경
	
	
	//조회
	@GetMapping("/")
	public List<VisitListVO> selectList(){
		return visitDao.selectList();
	}
	
}
