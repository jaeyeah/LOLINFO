package com.lol.lolinfo.vo.stat;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import com.lol.lolinfo.dto.StreamerDto;
import lombok.Data;

@Data
public class StreamerMonthlyStatVO {
    private StreamerDto streamer;
    private int year;
    private Summary summary;
    private List<Month> months;
    private Map<String, Integer> positions;
    private List<Opponent> opponents;

    @Data
    public static class Summary {
        private int participationCount;
        private int winCount;
        private int loseCount;
        private Double winRate;
    }
    @Data
    public static class Month {
        private int month;
        private int participationCount;
        private int winCount;
        private int loseCount;
        private Double winRate;
        private int cumulativeWinCount;
        private int cumulativeLoseCount;
        private Double cumulativeWinRate;
        private Double winRateChange;
        private Map<String, Integer> positions;
    }
    @Data
    public static class PositionRow {
        private int month;
        private String position;
        private int participationCount;
        private int winCount;
        private int loseCount;
    }
    @Data
    public static class Opponent {
        private int rank;
        private int streamerNo;
        private String streamerName;
        private String streamerSoopId;
        private int matchCount;
        private int winCount;
        private int loseCount;
        private Double winRate;
        private LocalDateTime lastMatchDate;
    }
}
