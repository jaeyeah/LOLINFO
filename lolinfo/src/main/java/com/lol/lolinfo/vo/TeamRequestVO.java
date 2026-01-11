package com.lol.lolinfo.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.StaffDto;
import com.lol.lolinfo.dto.TeamDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TeamRequestVO {

	private TeamDto teamDto;
	private List<StaffDto> staffDto;
}
