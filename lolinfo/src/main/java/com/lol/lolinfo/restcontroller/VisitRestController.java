package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.VisitDao;
import com.lol.lolinfo.dao.VisitUseDao;
import com.lol.lolinfo.dto.VisitDto;
import com.lol.lolinfo.dto.VisitUseDto;
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
	@Autowired
	private VisitUseDao visitUseDao;
	
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody VisitDto visitDto) {
		visitService.insert(visitDto);
	}
	
	//로그인시 상태변경 ->member컨트롤러에서 진행
	
	
	//조회
	@GetMapping("/month")
	public List<VisitListVO> selectMonth(@RequestParam String month){
		System.out.println("실행 : 월간통계");
		return visitDao.selectMonth(month);
	}
	@GetMapping("/year")
	public List<VisitListVO> selectYear(@RequestParam String year){
		System.out.println("실행 : 연간통계");
		return visitDao.selectYear(year);
	}
	
	//
	@GetMapping("/use")
	public List<VisitUseDto> selectUse(){
		return visitUseDao.selectList();
	}
}
