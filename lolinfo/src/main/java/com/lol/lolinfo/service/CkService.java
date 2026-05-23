package com.lol.lolinfo.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.vo.CkListVO;
import com.lol.lolinfo.vo.CkVO;

@Service
public class CkService {

	@Autowired
	private CkDao ckDao;
	
	@Transactional
	//CK+참여자 등록
	public void insert(CkVO ckVO) {
		int ckId = ckDao.sequence();
		ckVO.setCkId(ckId);
		ckDao.insert(ckVO); // CK등록
		if(ckVO.getParticipants() != null) {
			for(CkParticipantDto participant : ckVO.getParticipants()) {
				participant.setCkId(ckId);
				ckDao.insertParticipant(participant);
			}
		}
	}
	
	// ck 참여자 그루핑
	public List<CkVO> selectStreamer(int streamerNo){
		List<CkListVO> ckStreamers = ckDao.selectStreamer(streamerNo);
		
		Map<Integer, CkVO> map = new LinkedHashMap<>();
		
		for(CkListVO row : ckStreamers) {
			CkVO ck = map.get(row.getCkId());
			
			if(ck==null) {
				ck = CkVO.builder()
						.ckId(row.getCkId())
	                    .ckDate(row.getCkDate())
	                    .ckWinner(row.getCkWinner())
	                    .ckMemo(row.getCkMemo())
	                    .ckCreatedAt(row.getCkCreatedAt())
	                    .ckCreatedBy(row.getCkCreatedBy())
	                    .participants(new ArrayList<>())
	                    .build();
				map.put(row.getCkId(), ck);
			}
			CkParticipantDto participant = CkParticipantDto.builder()
	                .ckParticipantId(row.getCkParticipantId())
	                .ckId(row.getCkId())
	                .ckStreamer(row.getCkStreamer())
	                .ckSide(row.getCkSide())
	                .ckPosition(row.getCkPosition())
	                .streamerName(row.getStreamerName())
	                .build();

	        ck.getParticipants().add(participant);
		}
		return new ArrayList<>(map.values());
	}
}
