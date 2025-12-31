package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lol.lolinfo.dto.StreamerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StreamerListVO {
	// DB 데이터
	private Integer streamerNo;
	private String streamerSoopId;
	private String streamerName;
	// 가공데이터 (URL)
    // URL은 ‘게터에서’ 계산
    public String getStreamerStation() {
        if (streamerSoopId == null || streamerSoopId.isBlank()) return null;
        return "https://www.sooplive.co.kr/station/" + streamerSoopId;
    }
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
