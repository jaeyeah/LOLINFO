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
	private int teamId;
	private int tournamentId;
	private String teamName;
	private String teamRanking;
	private String teamNote;
	private int teamTop;
	private int teamJug;
	private int teamMid;
	private int teamAd;
	private int teamSup;
}
