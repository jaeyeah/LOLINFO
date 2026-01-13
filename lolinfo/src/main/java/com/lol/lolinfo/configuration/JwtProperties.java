package com.lol.lolinfo.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

//application.properties에 있는 custom.jwt 속성을 불러오기 위한 파일
@Data
@Component
// 이 클래스에 custom.jwt로 시작하는 속성들을 이름에 맞게 불러오겠다
@ConfigurationProperties(prefix = "custom.jwt")
public class JwtProperties {
	private String keyStr; // custion.jwt.key-str을 불러와서 저장
	private String issuer; // custion.jwt.issuer을 불러와서 저장
	private int expiration; // 만료시간(분)
	private int refreshExpiration; // 갱신토큰 만료시간(일)
	private int renewalLimit; // 갱신처리 기준시간(분)
}


