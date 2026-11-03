package com.lol.lolinfo.vo;

import lombok.Data;

@Data
public class ScrimDailyRecordVO {
    private String scrimDate;
    private int winCount;
    private int loseCount;

    public double getWinRate() {
        int total = winCount + loseCount;
        return total == 0 ? 0 : Math.round(winCount * 1000.0 / total) / 10.0;
    }
}
