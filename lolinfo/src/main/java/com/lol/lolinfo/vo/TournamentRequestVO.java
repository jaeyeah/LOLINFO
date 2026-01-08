package com.lol.lolinfo.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.HostDto;
import com.lol.lolinfo.dto.TournamentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TournamentRequestVO {

	private TournamentDto tournamentDto;
	private List<HostDto> hostDto;

}
