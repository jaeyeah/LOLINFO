package com.lol.lolinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.BookmarkDao;
import com.lol.lolinfo.dto.BookmarkDto;
import com.lol.lolinfo.vo.TokenVO;

@Service
public class BookmarkService {

	@Autowired
	private BookmarkDao bookmarkDao;
	@Autowired
	private TokenService tokenService;

	public boolean toggleStreamerBookmark(String bearerToken, int streamerId) {
		
		TokenVO tokenVO = tokenService.parse(bearerToken);
		String memberId = tokenVO.getLoginId();
		if (memberId == null) throw new RuntimeException("로그인이 필요합니다.");
		BookmarkDto bookmarkDto = new BookmarkDto();
		bookmarkDto.setBookmarkMember(memberId);
		bookmarkDto.setBookmarkType("streamer");
		bookmarkDto.setBookmarkTarget(streamerId);
		bookmarkDao.insert(bookmarkDto);
		// 삭제부터 진행
		if(bookmarkDao.delete(bookmarkDto)) return false;
		// 삭제 실패 -> 북마크 진행
		bookmarkDao.insert(bookmarkDto);
		return true;
	}

}
