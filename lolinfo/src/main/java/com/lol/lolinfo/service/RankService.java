package com.lol.lolinfo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.RankDao;
import com.lol.lolinfo.vo.CkRankingVO;
import com.lol.lolinfo.vo.CkStreakRankingResponseVO;
import com.lol.lolinfo.vo.CkStreakRankingVO;
import com.lol.lolinfo.vo.MyeolmangRankingVO;
import com.lol.lolinfo.vo.MyeolmangPositionRankingVO;

@Service
public class RankService {

    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT = 50;
    private static final int DEFAULT_MIN_PLAY_COUNT = 30;
    private static final Set<String> MYEOLMANG_POSITIONS =
            Set.of("ALL", "TOP", "JUNGLE", "MID", "ADC", "SUPPORT");

    private static final ZoneId KOREA_ZONE = ZoneId.of("Asia/Seoul");

    @Autowired
    private RankDao rankDao;

    public List<MyeolmangPositionRankingVO> getMyeolmangPositionRanking(String position) {
        String safePosition = position == null || position.isBlank()
                ? "ALL" : position.trim().toUpperCase(Locale.ROOT);
        if (!MYEOLMANG_POSITIONS.contains(safePosition)) {
            throw new IllegalArgumentException("지원하지 않는 멸망전 포지션입니다.");
        }
        return rankDao.selectMyeolmangPositionRanking(safePosition);
    }


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
            rankingList = rankDao.selectMaxStreakRanking(safeLimit);
        }
        else {
            rankingList = rankDao.selectCurrentStreakRanking(safeLimit);
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

        return rankDao.selectWinRanking(
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

        return rankDao.selectWinRateRanking(
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
    
    /**
     * 멸망전 입상 랭킹
     *
     * period
     * - all    : 역대 전체
     * - recent : 최근 3개 연도
     */
    public List<MyeolmangRankingVO> getMyeolmangRanking(
            String period,
            Integer limit) {

        int safeLimit = normalizeLimit(limit);

        String safePeriod =
                "recent".equalsIgnoreCase(period)
                        ? "recent"
                        : "all";

        int currentYear =
                LocalDate.now(KOREA_ZONE).getYear();

        // 2026년 기준 → 2024, 2025, 2026
        int startYear = currentYear - 2;

        return rankDao.selectMyeolmangRanking(
                safePeriod,
                startYear,
                safeLimit
        );
    }
}
