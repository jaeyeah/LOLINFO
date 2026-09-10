package com.lol.lolinfo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MyeolmangPositionRankingVO {
    private int streamerNo;
    private String streamerName;
    private String streamerSoopId;
    private String position;
    private int winCount;
    private int runnerUpCount;
    private int semifinalCount;
    private int tournamentCount;
    private int ranking;
}
