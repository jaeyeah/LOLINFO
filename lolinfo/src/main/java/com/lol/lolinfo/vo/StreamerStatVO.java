package com.lol.lolinfo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class StreamerStatVO {
	
	private int streamerNo;
	private String streamerSoopId;
	private String streamerName;

	// 공식 기록
	private int officialRanking1; //우승
	private int officialRanking2; //준우승
	private int officialRanking3; //4강

	// 전체 기록
	private int totalRanking1; // 전체 우승
	private int totalRanking2; // 전체 준우승
	private int totalRanking3; // 전체 4강
	
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
