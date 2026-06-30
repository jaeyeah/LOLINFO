package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.BookmarkDao;
import com.lol.lolinfo.service.BookmarkService;
import com.lol.lolinfo.service.TokenService;
import com.lol.lolinfo.vo.BookmarkStreamerVO;
import com.lol.lolinfo.vo.TokenVO;

@CrossOrigin
@RequestMapping("/api/bookmark")
@RestController
public class BookmarkRestController {
	@Autowired
	private BookmarkDao bookmarkDao;
	@Autowired
	private BookmarkService bookmarkService;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/streamer")
	public boolean toggleStreamer(@RequestHeader("Authorization") String bearerToken,
			@RequestParam int streamerId) {
		return bookmarkService.toggleStreamerBookmark(bearerToken, streamerId);
	}
	
	@PostMapping("/tournament")
	public boolean toggleTournament(@RequestHeader("Authorization") String bearerToken,
			@RequestParam int tournamentId) {
		return bookmarkService.toggleTournamentBookmark(bearerToken, tournamentId);
	}
	
	@GetMapping("/streamer")
	public List<BookmarkStreamerVO> selectList(@RequestHeader("Authorization") String bearerToken) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
        String memberId = tokenVO.getLoginId();
		return bookmarkDao.selectStreamerList(memberId);
	}
}
