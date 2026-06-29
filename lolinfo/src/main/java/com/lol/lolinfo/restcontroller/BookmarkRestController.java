package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.service.BookmarkService;

@CrossOrigin
@RequestMapping("/api/bookmark")
@RestController
public class BookmarkRestController {

	@Autowired
	private BookmarkService bookmarkService;
	
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
}
