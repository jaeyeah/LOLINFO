package com.lol.lolinfo.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.CkParticipantDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CkParticipantVO {

	private Integer ckStreamer;
	private String ckSide;
	private String ckPosition;
	private String streamerSoopId;
	private String streamerName;
	private String ckWinner;
	
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
