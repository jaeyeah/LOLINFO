package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamListVO {

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
	private String topName;
	private String jugName;
	private String midName;
	private String adName;
	private String supName;
	private String topId;
	private String jugId;
	private String midId;
	private String adId;
	private String supId;
	
}
