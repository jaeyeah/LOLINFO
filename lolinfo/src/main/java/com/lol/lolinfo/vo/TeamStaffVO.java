package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamStaffVO {

	private Integer staffStreamer;
	private Integer staffTeam;
	private String staffRole;
	
	private String streamerSoopId;
	private String streamerName;

}
