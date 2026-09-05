package com.lol.lolinfo.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class HomeBookmarkStreamerVO {

    private Integer streamerNo;
    private String streamerName;
    private String streamerSoopId;

    // 전체 CK 전적
    private Integer totalPlayCount;
    private Integer totalWinCount;
    private Integer totalLoseCount;
    private Double totalWinRate;

    // 최근 10경기 CK 전적
    private Integer recentPlayCount;
    private Integer recentWinCount;
    private Integer recentLoseCount;
    private Double recentWinRate;

    // 최근 경기 결과: W / L
    private List<String> recentResults = new ArrayList<>();
}