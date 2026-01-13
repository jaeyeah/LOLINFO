package com.lol.lolinfo.restcontroller;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.CertDao;
import com.lol.lolinfo.dto.CertDto;
import com.lol.lolinfo.service.EmailService;
import com.lol.lolinfo.vo.CertResultVO;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/cert")
public class CertRestController {
	@Autowired
	private EmailService emailService;
	@Autowired
	private CertDao certDao;
	
	//이메일 전송
	@PostMapping("/send")
	public void send(@Valid @RequestBody CertDto certDto) {
		emailService.sendCertNumber(certDto.getCertEmail());
	}
	
	//이메일 체크
	@PostMapping("/check")
	public CertResultVO check(@Valid @RequestBody CertDto certDto) {
		// [1] 이메일로 인증정보 조회
			CertDto findDto = certDao.selectOne(certDto.getCertEmail());
			if(findDto == null) return CertResultVO.builder() // 인증메일을 보낸 적이 없는 경우
											.result(false)
											.message("인증 내역이 존재하지 않습니다")
											.build();
		// [2] 유효시간 검사
			LocalDateTime current = LocalDateTime.now();
			LocalDateTime sent = findDto.getCertTime().toLocalDateTime();
			Duration duration = Duration.between(sent, current);
			if(duration.toSeconds()>600) return CertResultVO.builder()
											.result(false)
											.message("인증 시간이 만료되었습니다")
											.build(); 
		// [3] 인증번호 검사
			boolean isValid = certDto.getCertNumber().equals(findDto.getCertNumber());
			if(isValid==false) return CertResultVO.builder()
											.result(false)
											.message("인증번호가 일치하지 않습니다")
											.build(); 
		// 다 통과했으면 인증 완료
			certDao.delete(certDto.getCertEmail()); // 인증완료시 인증번호 데이터 삭제
			return CertResultVO.builder()
						.result(true)
						.message("인증이 완료되었습니다")
					.build(); 
	}
	
}
