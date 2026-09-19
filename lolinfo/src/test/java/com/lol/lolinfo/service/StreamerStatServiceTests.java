package com.lol.lolinfo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockMakers;
import org.springframework.web.server.ResponseStatusException;
import com.lol.lolinfo.dao.*;
import com.lol.lolinfo.dto.StreamerDto;
import com.lol.lolinfo.vo.stat.StreamerMonthlyStatVO.*;

class StreamerStatServiceTests {
    @Test void mybatisLoadsNestedResultTypesAndDateParameters() throws Exception {
        var configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        String resource = "mybatis/streamer-stat-mapper.xml";
        try (var reader = org.apache.ibatis.io.Resources.getResourceAsReader(resource)) {
            new org.apache.ibatis.builder.xml.XMLMapperBuilder(reader, configuration, resource,
                configuration.getSqlFragments()).parse();
        }
        var start = LocalDate.of(2026, 1, 1).atStartOfDay();
        var parameters = Map.of("streamerNo", 123, "start", start, "end", start.plusYears(1));
        for (String name : List.of("selectMonths", "selectOpponents")) {
            var statement = configuration.getMappedStatement("streamerStat." + name);
            var bound = statement.getBoundSql(parameters);
            assertFalse(bound.getSql().isBlank());
            for (var mapping : bound.getParameterMappings()) assertTrue(parameters.containsKey(mapping.getProperty()));
        }
    }

    private PositionRow row(int month, String position, int count, int wins, int losses) {
        var row = new PositionRow(); row.setMonth(month); row.setPosition(position);
        row.setParticipationCount(count); row.setWinCount(wins); row.setLoseCount(losses); return row;
    }
    @Test void cumulativeRatesUseUnroundedValuesAndCarryAcrossEmptyMonths() {
        var result = StreamerStatService.aggregate(2026, LocalDate.of(2026, 9, 19), List.of(
            row(1, "TOP", 4, 2, 2), row(2, "MID", 4, 3, 1),
            row(3, "AD", 5, 1, 4), row(4, "SUP", 5, 4, 1)));
        assertEquals(12, result.getMonths().size());
        assertEquals(50.0, result.getMonths().get(0).getCumulativeWinRate());
        assertNull(result.getMonths().get(0).getWinRateChange());
        assertEquals(62.5, result.getMonths().get(1).getCumulativeWinRate());
        assertEquals(12.5, result.getMonths().get(1).getWinRateChange());
        assertEquals(600.0 / 13, result.getMonths().get(2).getCumulativeWinRate());
        assertEquals(1000.0 / 18 - 600.0 / 13, result.getMonths().get(3).getWinRateChange(), 1e-10);
        assertEquals(1000.0 / 18, result.getMonths().get(4).getCumulativeWinRate());
        assertEquals(0.0, result.getMonths().get(4).getWinRateChange());
        assertNull(result.getMonths().get(9).getCumulativeWinRate());
        assertNull(result.getMonths().get(9).getWinRateChange());
        assertEquals(18, result.getSummary().getParticipationCount());
        assertEquals(10, result.getSummary().getWinCount());
        assertEquals(8, result.getSummary().getLoseCount());
        result.getMonths().forEach(m -> assertEquals(m.getParticipationCount(), m.getPositions().values().stream().mapToInt(Integer::intValue).sum()));
        assertEquals(18, result.getPositions().values().stream().mapToInt(Integer::intValue).sum());
    }
    @Test void undecidedGamesCountAsParticipationButDoNotCreateZeroPercentRates() {
        var result = StreamerStatService.aggregate(2026, LocalDate.of(2026, 9, 19), List.of(
            row(2, "JUG", 3, 0, 0), row(3, "MID", 2, 1, 1), row(3, "SUP", 1, 0, 0)));
        assertNull(result.getMonths().get(0).getCumulativeWinRate());
        assertNull(result.getMonths().get(1).getCumulativeWinRate());
        assertNull(result.getMonths().get(2).getWinRateChange());
        assertEquals(50.0, result.getSummary().getWinRate());
        assertEquals(6, result.getSummary().getParticipationCount());
        assertEquals(50.0, result.getMonths().get(8).getCumulativeWinRate());
    }
    @Test void noGamesAndFutureYearsHaveNoRates() {
        for (int year : List.of(2025, 2026, 2027)) {
            var result = StreamerStatService.aggregate(year, LocalDate.of(2026, 9, 19), List.of());
            assertNull(result.getSummary().getWinRate());
            result.getMonths().forEach(m -> assertNull(m.getCumulativeWinRate()));
        }
    }
    @Test void yearSwitchUsesSameRangeForBothQueriesAndResetsTotals() {
        var streamers = mock(StreamerDao.class, withSettings().mockMaker(MockMakers.SUBCLASS)); var stats = mock(StreamerStatDao.class, withSettings().mockMaker(MockMakers.SUBCLASS));
        when(streamers.selectOne(123)).thenReturn(StreamerDto.builder().streamerNo(123).build());
        var opponent = new Opponent(); opponent.setWinCount(8); opponent.setLoseCount(4); opponent.setMatchCount(13);
        when(stats.selectMonths(eq(123), any(), any())).thenReturn(List.of(row(1, "MID", 4, 2, 2)), List.of());
        when(stats.selectOpponents(eq(123), any(), any())).thenReturn(List.of(opponent), List.of());
        var service = new StreamerStatService(streamers, stats);
        int year = Year.now(ZoneId.of("Asia/Seoul")).getValue() - 2;
        var first = service.getMonthlyStat(123, year); var second = service.getMonthlyStat(123, year + 1);
        assertEquals(800.0 / 12, first.getOpponents().get(0).getWinRate());
        assertEquals(1, first.getOpponents().get(0).getRank());
        assertEquals(0, second.getSummary().getParticipationCount()); assertTrue(second.getOpponents().isEmpty());
        for (int y : List.of(year, year + 1)) {
            var start = LocalDate.of(y, 1, 1).atStartOfDay();
            verify(stats).selectMonths(123, start, start.plusYears(1));
            verify(stats).selectOpponents(123, start, start.plusYears(1));
        }
    }
    @Test void currentYearStopsAtQueryTimeAndBadInputDoesNotQueryStats() {
        var streamers = mock(StreamerDao.class, withSettings().mockMaker(MockMakers.SUBCLASS)); var stats = mock(StreamerStatDao.class, withSettings().mockMaker(MockMakers.SUBCLASS));
        when(streamers.selectOne(123)).thenReturn(new StreamerDto());
        when(stats.selectMonths(anyInt(), any(), any())).thenReturn(List.of());
        when(stats.selectOpponents(anyInt(), any(), any())).thenReturn(List.of());
        var service = new StreamerStatService(streamers, stats);
        var before = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        service.getMonthlyStat(123, before.getYear());
        var end = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(stats).selectMonths(eq(123), eq(LocalDate.of(before.getYear(), 1, 1).atStartOfDay()), end.capture());
        assertFalse(end.getValue().isBefore(before));
        assertFalse(end.getValue().isAfter(LocalDateTime.now(ZoneId.of("Asia/Seoul"))));
        assertThrows(ResponseStatusException.class, () -> service.getMonthlyStat(123, 9999));
        assertThrows(ResponseStatusException.class, () -> service.getMonthlyStat(0, 2026));
        assertThrows(ResponseStatusException.class, () -> service.getMonthlyStat(999, 2026));
    }
}
