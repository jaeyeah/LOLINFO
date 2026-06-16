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
public class ScrimVsRecordVO {
	private int vsTeam;
    private String vsTeamName;

    private int vsWinCount;
    private int vsLoseCount;
    public double getVsWinRate() {
    	int total = vsWinCount +vsLoseCount;
    	if(total==0) return 0;
    	return Math.round((vsWinCount * 1000.0 / total)) / 10.0;
    };
}
