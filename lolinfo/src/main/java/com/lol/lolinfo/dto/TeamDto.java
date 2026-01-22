package com.lol.lolinfo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.vo.TeamListVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamDto {
	private Integer teamId;
	private int tournamentId;
	private String teamName;
	private String teamRanking;
	private Integer teamTop;
	private Integer teamJug;
	private Integer teamMid;
	private Integer teamAd;
	private Integer teamSup;
}
