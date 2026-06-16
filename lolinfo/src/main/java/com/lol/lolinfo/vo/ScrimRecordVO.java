package com.lol.lolinfo.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.CkParticipantDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ScrimRecordVO {
	private int scrimTournament;
    private int scrimTeam;
    private String teamName;

    private int scrimWinCount;
    private int scrimLoseCount;
    public double getScrimWinRate() {
    	int total = scrimWinCount +scrimLoseCount;
    	if(total==0) return 0;
    	return Math.round((scrimWinCount * 1000.0 / total)) / 10.0;
    };
}
