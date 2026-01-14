package com.lol.lolinfo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JWT 토큰을 해석해서 얻고싶은 정보
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TokenVO {
	private String loginId;
	private String loginLevel;
	private String loginNickname;
}
