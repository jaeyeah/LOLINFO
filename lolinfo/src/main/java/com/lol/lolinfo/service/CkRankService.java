package com.lol.lolinfo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.CkRankDao;
import com.lol.lolinfo.vo.CkRankingVO;
import com.lol.lolinfo.vo.CkStreakRankingResponseVO;
import com.lol.lolinfo.vo.CkStreakRankingVO;

@Service
public class CkRankService {

    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT = 50;
    private static final int DEFAULT_MIN_PLAY_COUNT = 30;

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    @Autowired
    private CkRankDao ckRankDao;


    /**
     * 현재 / 역대 연승·연패 랭킹
     *
     * type
     * - current : 현재 연승 / 연패
     * - max     : 역대 최다 연승 / 연패
     */
    public CkStreakRankingResponseVO getStreakRanking(
            String type,
            Integer limit) {

        int safeLimit = normalizeLimit(limit);

        List<CkStreakRankingVO> rankingList;

        if ("max".equalsIgnoreCase(type)) {
            rankingList = ckRankDao.selectMaxStreakRanking(safeLimit);
        }
        else {
            rankingList = ckRankDao.selectCurrentStreakRanking(safeLimit);
        }

        List<CkStreakRankingVO> winList = rankingList.stream()
                .filter(rank -> "W".equals(rank.getResult()))
                .toList();

        List<CkStreakRankingVO> loseList = rankingList.stream()
                .filter(rank -> "L".equals(rank.getResult()))
                .toList();

        return CkStreakRankingResponseVO.builder()
                .winList(winList)
                .loseList(loseList)
                .build();
    }


    /**
     * CK 다승 랭킹
     *
     * period
     * - all  : 역대
     * - year : 특정 연도
     */
    public List<CkRankingVO> getWinRanking(
            String period,
            Integer year,
            Integer limit) {

        int safeLimit = normalizeLimit(limit);

        String safePeriod =
                "year".equalsIgnoreCase(period)
                        ? "year"
                        : "all";

        int safeYear;

        if ("year".equals(safePeriod)) {

            safeYear = year != null
                    ? year
                    : LocalDate.now(KOREA_ZONE).getYear();

        }
        else {
            // all에서는 mapper에서 사용하지 않음
            safeYear = LocalDate.now(KOREA_ZONE).getYear();
        }

        return ckRankDao.selectWinRanking(
                safePeriod,
                safeYear,
                safeLimit
        );
    }


    /**
     * 역대 CK 승률 랭킹
     */
    public List<CkRankingVO> getWinRateRanking(
            Integer minPlayCount,
            Integer limit) {

        int safeLimit = normalizeLimit(limit);

        int safeMinPlayCount =
                minPlayCount == null || minPlayCount < 1
                        ? DEFAULT_MIN_PLAY_COUNT
                        : minPlayCount;

        return ckRankDao.selectWinRateRanking(
                safeMinPlayCount,
                safeLimit
        );
    }


    /**
     * 랭킹 출력 개수 검증
     */
    private int normalizeLimit(Integer limit) {

        if (limit == null || limit < 1) {
            return DEFAULT_LIMIT;
        }

        return Math.min(limit, MAX_LIMIT);
    }
}