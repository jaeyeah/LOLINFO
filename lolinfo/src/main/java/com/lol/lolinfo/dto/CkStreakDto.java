package com.lol.lolinfo.dto;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CkStreakDto {

	private Integer streamerNo;

    private String currentResult;
    private Integer currentStreak;

    private Integer maxWinStreak;
    private Date maxWinStart;
    private Date maxWinEnd;

    private Integer maxLoseStreak;
    private Date maxLoseStart;
    private Date maxLoseEnd;

    private Timestamp streakUpdatedAt;
	
}
