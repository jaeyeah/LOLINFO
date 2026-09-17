package com.lol.lolinfo.vo.stat;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StatMonthlyVO {

    private Integer year;

    private List<Month> months;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Month {

    	private Integer month;

        private Integer ckCount;

        private Integer participantCount;

        private Integer tournamentCount;

        private List<StatTournamentVO> tournaments;
    }
}