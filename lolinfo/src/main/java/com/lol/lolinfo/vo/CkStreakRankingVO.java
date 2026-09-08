package com.lol.lolinfo.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CkStreakRankingVO {

    private Integer rank;

    private Integer streamerNo;
    private String streamerName;
    private String streamerSoopId;

    // W / L
    private String result;

    private Integer streakCount;

    private LocalDate streakStartDate;
    private LocalDate streakEndDate;
}