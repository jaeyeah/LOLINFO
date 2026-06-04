package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dto.BoardDto;
import com.lol.lolinfo.service.BoardService;

@CrossOrigin
@RestController
@RequestMapping("/api/board")
public class BoardRestController {

	@Autowired
	private BoardService boardService;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody BoardDto boardDto,
			@RequestHeader("Authorization") String bearerToken) {
		boardService.insert(boardDto, bearerToken);
	}
	

}
