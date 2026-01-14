package com.lol.lolinfo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MemberLoginResponseVO {
	
	private String loginId;
	private String loginLevel;
	private String loginNickname;
	private String accessToken;
	private String refreshToken;

}
