package com.lol.lolinfo.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ScrimDto {

	private int scrimId;
	private int scrimTournament;
	private int scrimRedTeam;
	private int scrimRedScore;
	private int scrimBlueTeam;
	private int scrimBlueScore;
	private Date scrimDate;
	private String scrimCreatedBy;
}
