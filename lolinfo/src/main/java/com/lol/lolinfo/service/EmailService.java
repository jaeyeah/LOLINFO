package com.lol.lolinfo.service;

import java.text.DecimalFormat;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.CertDao;
import com.lol.lolinfo.dto.CertDto;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;
	@Autowired
	private CertDao certDao;

	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		sender.send(message);
	}

	public void sendCertNumber(String email) {
		// 랜덤번호 생성
		Random r = new Random();
		int number = r.nextInt(1000000);
		DecimalFormat df= new DecimalFormat("000000");
		String certNumber = df.format(number);
		
		// 메세지 생성 및 전송
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("[LoL-Info] 회원가입용 인증번호 전송");
		message.setText("인증번호는 ["+certNumber+"] 입니다");
		sender.send(message);
		
		// 인증번호를 DB에 저장하는 코드
		CertDto certDto = certDao.selectOne(email);
		if(certDto==null) { // 인증메일을 보낸 기록이 없다면
			certDao.insert(CertDto.builder()
						.certEmail(email).certNumber(certNumber)
					.build());
		}
		else { // 인증메일을 보낸 기록이 있다면
			certDao.update(CertDto.builder()
						.certEmail(email).certNumber(certNumber)
					.build());
		}
		
	}


}
