package com.lol.lolinfo.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.BookmarkDao;
import com.lol.lolinfo.dto.BookmarkDto;
import com.lol.lolinfo.vo.HomeBookmarkCkResultVO;
import com.lol.lolinfo.vo.HomeBookmarkStreamerVO;
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
    public String getLoginId(String bearerToken) {
        TokenVO tokenVO = tokenService.parse(bearerToken);
        String memberId = tokenVO.getLoginId();
        if (memberId == null)  throw new RuntimeException("로그인이 필요합니다.");
        return memberId;
    }
    // DTO에 집어넣기
    public BookmarkDto createBookmarkDto(String bearerToken, String type, int target) {
        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setBookmarkMember(getLoginId(bearerToken));
        bookmarkDto.setBookmarkType(type);
        bookmarkDto.setBookmarkTarget(target);
        return bookmarkDto;
    }
    // 북마크 토글화
    public boolean toggleBookmark(String bearerToken, String type, int target) {
        BookmarkDto bookmarkDto = createBookmarkDto(bearerToken, type, target);
        // 1. 삭제부터 진행(false) → 삭제될게 없으면 등록(true)
        if (bookmarkDao.delete(bookmarkDto)) return false;
        bookmarkDao.insert(bookmarkDto);
        return true;
    }
    
    //메인페이지 북마크목록
    public List<HomeBookmarkStreamerVO> selectHomeStreamerList(String bearerToken) {
    	TokenVO tokenVO = tokenService.parse(bearerToken);
        String memberId = tokenVO.getLoginId();
        List<HomeBookmarkStreamerVO> streamers = bookmarkDao.selectHomeBookmarkStreamerList(memberId);
        if (streamers.isEmpty()) {return streamers;}

        Map<Integer, List<String>> resultMap =
                bookmarkDao.selectHomeBookmarkStreamerRecentCkList(memberId)
                        .stream()
                        .collect(Collectors.groupingBy(
                                HomeBookmarkCkResultVO::getStreamerNo,
                                Collectors.mapping(
                                        HomeBookmarkCkResultVO::getResult,
                                        Collectors.toList()
                                )
                        ));

        for (HomeBookmarkStreamerVO streamer : streamers) {
            List<String> results = resultMap.getOrDefault(
                    streamer.getStreamerNo(),
                    List.of()
            );

            int recentWinCount = (int) results.stream()
                    .filter("W"::equals)
                    .count();

            streamer.setRecentResults(results);
            streamer.setRecentPlayCount(results.size());
            streamer.setRecentWinCount(recentWinCount);
            streamer.setRecentLoseCount(results.size() - recentWinCount);
            streamer.setRecentWinRate(
                    results.isEmpty()? 0.0
                            : Math.round(recentWinCount * 1000.0 / results.size()) / 10.0
            );
        }

        return streamers;
    }
    
    
}
