package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dto.VisitDto;
import com.lol.lolinfo.service.VisitService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/visit")
@Slf4j
@CrossOrigin
public class VisitRestController {

	@Autowired
	private VisitService visitService;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody VisitDto visitDto) {
		visitService.insert(visitDto);
	}
	
}
