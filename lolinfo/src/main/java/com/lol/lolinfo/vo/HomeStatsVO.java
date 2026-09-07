package com.lol.lolinfo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class HomeStatsVO {
	
	private int todayVisitors;
	private int streamerCount;
	private int tournamentCount;
	private int ckCount;

}
