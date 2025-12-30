package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.StreamerDao;
import com.lol.lolinfo.dto.StreamerDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/streamer")
public class StreamerRestController {

	@Autowired
	private StreamerDao streamerDao;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody StreamerDto streamerDto) {
		streamerDao.insert(streamerDto);
	}
	
	//전체 조회
	@GetMapping("/")
	public List<StreamerDto> selectList(){
		return streamerDao.selectList();
	}

	//상세 조회
	@GetMapping("/{streamerNo}")
	public StreamerDto selectOne(@PathVariable int streamerNo) {
		return streamerDao.selectOne(streamerNo);
	}

	//수정
	
	//삭제
	@DeleteMapping("/{streamerNo}")
	public void delete(@PathVariable int streamerNo) {
		streamerDao.delete(streamerNo);
	}
	
}
