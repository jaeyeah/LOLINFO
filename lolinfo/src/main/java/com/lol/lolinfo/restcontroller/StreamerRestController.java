package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.StreamerDao;
import com.lol.lolinfo.dto.StreamerDto;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;
import com.lol.lolinfo.vo.StreamerStatVO;

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
		System.out.println("스트리머 등록 실행");
	}
	
	//전체 조회
	@GetMapping("/")
	public PageResponseVO<StreamerStatVO> selectList(@RequestParam(defaultValue = "1") int page){
		int totalCount = streamerDao.count();
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setTotalCount(totalCount);
		List<StreamerStatVO> list = streamerDao.selectList(pageVO);
		return new PageResponseVO<>(list, pageVO);
	}
	@GetMapping("/totalList")
	public PageResponseVO<StreamerStatVO> selectTotalRankingList(@RequestParam(defaultValue = "1") int page){
		int totalCount = streamerDao.count();
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setTotalCount(totalCount);
		List<StreamerStatVO> list = streamerDao.selectTotalRankingList(pageVO);
		return new PageResponseVO<>(list, pageVO);
	}
	

	//상세 조회
	@GetMapping("/{streamerNo}")
	public StreamerStatVO selectOnePlusStat(@PathVariable int streamerNo) {
		return streamerDao.selectOnePlusStat(streamerNo);
	}
	
	//상세 조회
	@GetMapping("/no/{streamerNo}")
	public StreamerDto selectOne(@PathVariable int streamerNo) {
		return streamerDao.selectOne(streamerNo);
	}

	//수정
	@PutMapping("/")
	public void update(@RequestBody StreamerDto streamerDto) {
		streamerDao.update(streamerDto);
		System.out.println("스트리머 수정 실행");
	}
	//삭제
	@DeleteMapping("/{streamerNo}")
	public void delete(@PathVariable int streamerNo) {
		streamerDao.delete(streamerNo);
	}
	
}
