package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.ScrimDao;
import com.lol.lolinfo.dto.ScrimDto;
import com.lol.lolinfo.error.TargetNotfoundException;
import com.lol.lolinfo.service.TokenService;
import com.lol.lolinfo.vo.TokenVO;

@CrossOrigin
@RestController
@RequestMapping("/api/scrim")
public class ScrimRestController {

	@Autowired
	private ScrimDao scrimDao;
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/")
	public void insert(@RequestBody ScrimDto scrimDto,
			@RequestHeader("Authorization") String bearerToken
			) {
		TokenVO tokenVO = tokenService.parse(bearerToken);
		if(tokenVO == null) throw new TargetNotfoundException();
		scrimDto.setScrimCreatedBy(tokenVO.getLoginId());
		scrimDao.insert(scrimDto);
	}
	
	
}
