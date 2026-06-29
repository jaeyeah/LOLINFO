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

    /// 북마킹할 목록들 ** ""만 바꿔주기
    // 스트리머 즐겨찾기
    public boolean toggleStreamerBookmark(String bearerToken, int streamerId) {
        return toggleBookmark(bearerToken, "streamer", streamerId);
    }
    // 대회 즐겨찾기
    public boolean toggleTournamentBookmark(String bearerToken, int tournamentId) {
        return toggleBookmark(bearerToken, "tournament", tournamentId);
    }
    
    
    /// 북마킹 서비스화
    // 로그인ID 반환 및 검사
    private String getLoginId(String bearerToken) {
        TokenVO tokenVO = tokenService.parse(bearerToken);
        String memberId = tokenVO.getLoginId();
        if (memberId == null)  throw new RuntimeException("로그인이 필요합니다.");
        return memberId;
    }
    // DTO에 집어넣기
    private BookmarkDto createBookmarkDto(String bearerToken, String type, int target) {
        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setBookmarkMember(getLoginId(bearerToken));
        bookmarkDto.setBookmarkType(type);
        bookmarkDto.setBookmarkTarget(target);
        return bookmarkDto;
    }
    // 북마크 토글화
    private boolean toggleBookmark(String bearerToken, String type, int target) {
        BookmarkDto bookmarkDto = createBookmarkDto(bearerToken, type, target);
        // 1. 삭제부터 진행(false) → 삭제될게 없으면 등록(true)
        if (bookmarkDao.delete(bookmarkDto)) return false;
        bookmarkDao.insert(bookmarkDto);
        return true;
    }
}
