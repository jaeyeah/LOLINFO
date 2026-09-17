package com.lol.lolinfo.vo.stat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatTournamentVO {

	 @JsonIgnore
	private Integer month;

    private Integer id;

    private String name;
}