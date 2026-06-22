package com.lol.lolinfo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lol.lolinfo.dao.StreamerWithDao;
import com.lol.lolinfo.vo.StreamerWithCkVO;
import com.lol.lolinfo.vo.StreamerWithTournamentVO;

@Service
public class StreamerService {

	@Autowired
	private StreamerWithDao streamerWithDao;
	
	public List<StreamerWithCkVO> withCk(int streamerNo){
		return streamerWithDao.withCk(streamerNo);
	}
	public List<StreamerWithTournamentVO> withTournament(int streamerNo){
		return streamerWithDao.withTournament(streamerNo);
	}
	
}
