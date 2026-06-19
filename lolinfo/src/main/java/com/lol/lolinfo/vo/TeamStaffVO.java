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
	
	public String getStreamerProfile() {
    	if (streamerSoopId == null || streamerSoopId.isBlank()) {
            return "https://profile.img.sooplive.co.kr/LOGO/af" ;} // 기본이미지
    	String prefix = streamerSoopId.substring(0, 2);
    	return "https://profile.img.sooplive.co.kr/LOGO/" 
        		+ prefix + "/" 
        		+ streamerSoopId + "/" 
        		+ streamerSoopId + ".jpg";
	}
}
