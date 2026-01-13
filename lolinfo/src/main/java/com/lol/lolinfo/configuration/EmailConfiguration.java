package com.lol.lolinfo.configuration;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

// 이메일 설정양식 자동화 저장

// 설정파일을 이용하면 내가 자주 쓰는 도구를 등록해둘 수 있다
@Configuration
public class EmailConfiguration {
	
	
	// 스프링에서는 서버가 시작되면 @Bean을 자동으로 등록하도록 설계되어있다.
	@Bean
	public JavaMailSenderImpl sender() {
		
		//메일 발송 도구 생성
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		
		// 서비스 제공자 정보 설정
		sender.setHost("smtp.gmail.com"); // 이용할 업체의 호스트 정보
		sender.setPort(587); // 이용할 업체의 포트 번호 // port = 네트워크를 사용하는 프로세스 번호
		sender.setUsername("jaeyeah819"); // 이용할 업체의 사용자 계정이름 (자격이 있는 계정)
		sender.setPassword("qbvkkfwqmhoczaxg"); // 업체에서 발급받은 비밀번호 (구글은 앱 비밀번호)
		
			// 추가 옵션 정보 (gmail용)
			Properties props = new Properties(); // 추가 정보를 담을 저장소 (String, String 형태의 Map)
			props.setProperty("mail.smtp.auth", "true"); // 이메일 발송에 인증을 사용 (무조건 true)
			props.setProperty("mail.smtp.debug", "true"); // 이메일 발송 과정을 자세하게 출력(오류 해결용)
			props.setProperty("mail.smtp.starttls.enable", "true"); // STARTTLS (보안용 통신방식)
			props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2"); // TLS 방식의 보안 버전 선택
			props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");//신뢰할 수 있는 인증서 발급자 지정
			sender.setJavaMailProperties(props);
		
		return sender;
	}
	
}
