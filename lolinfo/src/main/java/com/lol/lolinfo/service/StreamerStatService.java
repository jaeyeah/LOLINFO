package com.lol.lolinfo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.lol.lolinfo.dao.StreamerDao;
import com.lol.lolinfo.dao.StreamerStatDao;
import com.lol.lolinfo.dao.VisitUseDao;
import com.lol.lolinfo.vo.stat.StreamerMonthlyStatVO;
import com.lol.lolinfo.vo.stat.StreamerMonthlyStatVO.Month;
import com.lol.lolinfo.vo.stat.StreamerMonthlyStatVO.PositionRow;
import com.lol.lolinfo.vo.stat.StreamerMonthlyStatVO.Summary;

@Service
public class StreamerStatService {
    private final StreamerDao streamerDao;
    private final StreamerStatDao statDao;
    private final VisitUseDao visitUseDao;
    
    public StreamerStatService(StreamerDao streamerDao, StreamerStatDao statDao, VisitUseDao visitUseDao) {
        this.streamerDao = streamerDao;
        this.statDao = statDao;
        this.visitUseDao = visitUseDao;
    }
    @Transactional(readOnly = true)
    public StreamerMonthlyStatVO getMonthlyStat(int streamerNo, int year) {
        if (streamerNo < 1 || year < 1 || year > 9998)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 조회 조건입니다.");
        var streamer = streamerDao.selectOne(streamerNo);
        if (streamer == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "스트리머가 없습니다.");
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime nextYear = start.plusYears(1);
        LocalDateTime end = nextYear.isBefore(now) ? nextYear : now;
        var result = aggregate(year, now.toLocalDate(), statDao.selectMonths(streamerNo, start, end));
        result.setStreamer(streamer);
        var opponents = statDao.selectOpponents(streamerNo, start, end);
        for (int i = 0; i < opponents.size(); i++) {
            var opponent = opponents.get(i);
            opponent.setRank(i + 1);
            opponent.setWinRate(rate(opponent.getWinCount(), opponent.getLoseCount()));
        }
        result.setOpponents(opponents);
        visitUseDao.increase("monthlyStat");
        return result;
    }
    private static Map<String, Integer> emptyPositions() {
        Map<String, Integer> positions = new LinkedHashMap<>();
        for (String position : List.of("TOP", "JUG", "MID", "AD", "SUP")) positions.put(position, 0);
        return positions;
    }
    static Double rate(int wins, int losses) {
        return wins + losses == 0 ? null : 100.0 * wins / (wins + losses);
    }
    // Keep unrounded rates in the response; presentation alone rounds to one decimal.
    static StreamerMonthlyStatVO aggregate(int year, LocalDate today, List<PositionRow> rows) {
        var result = new StreamerMonthlyStatVO();
        result.setYear(year);
        result.setPositions(emptyPositions());
        List<Month> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            var month = new Month(); month.setMonth(i); month.setPositions(emptyPositions()); months.add(month);
        }
        for (var row : rows) {
            var month = months.get(row.getMonth() - 1);
            month.setParticipationCount(month.getParticipationCount() + row.getParticipationCount());
            month.setWinCount(month.getWinCount() + row.getWinCount());
            month.setLoseCount(month.getLoseCount() + row.getLoseCount());
            if (!month.getPositions().containsKey(row.getPosition()))
                throw new IllegalStateException("Unknown CK position: " + row.getPosition());
            month.getPositions().merge(row.getPosition(), row.getParticipationCount(), Integer::sum);
            result.getPositions().merge(row.getPosition(), row.getParticipationCount(), Integer::sum);
        }
        var summary = new Summary();
        Double previousRate = null;
        for (var month : months) {
            summary.setParticipationCount(summary.getParticipationCount() + month.getParticipationCount());
            summary.setWinCount(summary.getWinCount() + month.getWinCount());
            summary.setLoseCount(summary.getLoseCount() + month.getLoseCount());
            month.setWinRate(rate(month.getWinCount(), month.getLoseCount()));
            month.setCumulativeWinCount(summary.getWinCount());
            month.setCumulativeLoseCount(summary.getLoseCount());
            if (!YearMonth.of(year, month.getMonth()).isAfter(YearMonth.from(today))) {
                Double currentRate = rate(summary.getWinCount(), summary.getLoseCount());
                month.setCumulativeWinRate(currentRate);
                month.setWinRateChange(previousRate == null || currentRate == null ? null : currentRate - previousRate);
                previousRate = currentRate;
            }
        }
        summary.setWinRate(rate(summary.getWinCount(), summary.getLoseCount()));
        result.setSummary(summary); result.setMonths(months);
        return result;
    }
}
