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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.BoardDao;
import com.lol.lolinfo.dto.BoardDto;
import com.lol.lolinfo.service.BoardService;
import com.lol.lolinfo.vo.BoardListVO;
import com.lol.lolinfo.vo.ScrimUpdateVO;

@CrossOrigin
@RestController
@RequestMapping("/api/board")
public class BoardRestController {

	@Autowired
	private BoardService boardService;
	@Autowired
	private BoardDao boardDao;
	
	//등록
	@PostMapping("/")
	public void insert(@RequestBody BoardDto boardDto,
			@RequestHeader("Authorization") String bearerToken) {
		boardService.insert(boardDto, bearerToken);
	}
	
	//목록
	@GetMapping("/")
	public List<BoardListVO> list() {
	    return boardDao.selectBoardList();
	}
	@GetMapping("/{boardId}")
	public BoardDto list(@PathVariable int boardId) {
	    return boardDao.selectOne(boardId);
	}
	
	//수정
	@PutMapping("/")
	public void edit(@RequestBody BoardDto boardDto,
	        @RequestHeader("Authorization") String bearerToken) {
	    boardService.edit(boardDto, bearerToken);
	    System.out.println("게시글 수정 실행");
	}
	//삭제
	@DeleteMapping("/{boardId}")
	public void delete(@PathVariable int boardId,
			@RequestHeader("Authorization") String bearerToken) {
		boardService.delete(boardId, bearerToken);
		System.out.println("게시글 삭제 실행");
	}
	
	
}
