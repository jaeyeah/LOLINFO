package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.HostDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StaffListVO {

	private Integer staffStreamer;
	private Integer staffTeam;
	private String staffRole;
	private String streamerName;
	private String streamerSoopId;
	private String teamName;
	private String teamRanking;
	private Integer tournamentYear;
	private String tournamentName;
}
