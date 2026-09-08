package com.lol.lolinfo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lol.lolinfo.dao.CkDao;
import com.lol.lolinfo.dao.CkParticipantDao;
import com.lol.lolinfo.dao.CkStreakDao;
import com.lol.lolinfo.dto.CkDto;
import com.lol.lolinfo.dto.CkParticipantDto;
import com.lol.lolinfo.error.NeedPermissionException;
import com.lol.lolinfo.error.TargetNotfoundException;
import com.lol.lolinfo.error.UnauthorizationException;
import com.lol.lolinfo.vo.CkListVO;
import com.lol.lolinfo.vo.CkPeriodVO;
import com.lol.lolinfo.vo.CkVO;
import com.lol.lolinfo.vo.CkVsVO;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.TokenVO;

import io.jsonwebtoken.JwtException;


@Service
public class CkService {

	@Autowired
	private CkDao ckDao;
	@Autowired
	private CkStreakDao ckStreakDao;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CkStreakService ckStreakService;
	@Autowired
	private CkParticipantDao ckParticipantDao;
	
	//CK 등록
	@Transactional
	public void insert(CkVO ckVO) {
		// CK 등록
	    int ckId = ckDao.sequence();
	    ckVO.setCkId(ckId);
	    ckDao.insert(ckVO);

	    if (ckVO.getParticipants() != null) {

	        Set<Integer> streamerNos = new HashSet<>();
	        for (CkParticipantDto participant : ckVO.getParticipants()) {
	            participant.setCkId(ckId); // CK참가자에 CKID 부여
	            streamerNos.add(participant.getCkStreamer()); // refresh를 위한 참가자id set저장
	        }
	        // ckid 저장
	        ckDao.insertParticipantAll(ckVO.getParticipants());
	        // 연승 통계 갱신
	        ckStreakService.refreshAll(streamerNos);
	    }
	}
	
	public PageResponseVO<CkListVO> selectListByStreamer(int streamerNo, int page){
		return selectListByStreamer(new CkPeriodVO(streamerNo, page, null, null));
	}

	public PageResponseVO<CkListVO> selectListByStreamer(CkPeriodVO query){
		query.setTotalCount(ckDao.countByStreamer(query));
		query.setPage(Math.min(query.getPage(), Math.max(1, query.getTotalPage())));
		List<CkListVO> list = ckDao.selectListByStreamer(query);
		return new PageResponseVO<>(list, query);
	}
	
	public List<CkVsVO> selectVsList(int streamerNo){
		return selectVsList(new CkPeriodVO(streamerNo, 1, null, null));
	}

	public List<CkVsVO> selectVsList(CkPeriodVO query){
		return ckDao.selectVsList(query);
	}
	
	
	// 로그인 검사
	private TokenVO requireLogin(String bearerToken) {
	    if (bearerToken == null || bearerToken.isBlank()) throw new UnauthorizationException();
	    
	    TokenVO tokenVO;

	    try {
	        tokenVO = tokenService.parse(bearerToken);
	    } catch (JwtException | IllegalArgumentException e) {
	        throw new UnauthorizationException();
	    }

	    if (tokenVO == null
	            || tokenVO.getLoginId() == null
	            || tokenVO.getLoginId().isBlank()) {
	        throw new UnauthorizationException();
	    }
	    return tokenVO;
	}

	// 수정·삭제 권한 검사 후 원본 CK 반환
	private CkDto requireManagePermission(
	        int ckId, String bearerToken) {
	    // 1. 로그인 확인
	    TokenVO tokenVO = requireLogin(bearerToken);
	    // 2. DB에서 원본 조회
	    CkDto originDto = ckDao.selectOne(ckId);
	    if (originDto == null) throw new TargetNotfoundException();

	    // 3. 작성자 또는 관리자 여부 확인
	    boolean isOwner = tokenVO.getLoginId()
	            .equals(originDto.getCkCreatedBy());
	    boolean isAdmin = "관리자"
	            .equals(tokenVO.getLoginLevel());
	    if (!isOwner && !isAdmin) throw new NeedPermissionException();
	    return originDto;
	}

	//부분 수정
	@Transactional
	public CkDto updateUnit(
	        int ckId,
	        CkDto ckDto,
	        String bearerToken) {

	    CkDto originDto =
	            requireManagePermission(ckId, bearerToken);

	    boolean streakChanged =
	            ckDto.getCkDate() != null
	            || ckDto.getCkWinner() != null;
	    
	    // 수정 가능한 항목만 반영
	    if (ckDto.getCkDate() != null) {
	        originDto.setCkDate(ckDto.getCkDate());
	    }

	    if (ckDto.getCkMemo() != null) {
	        originDto.setCkMemo(ckDto.getCkMemo());
	    }

	    if (ckDto.getCkWinner() != null) {
	        originDto.setCkWinner(ckDto.getCkWinner());
	    }

	    if (!ckDao.updateUnit(originDto)) {
	        throw new TargetNotfoundException();
	    }

	    // 이 CK에 참여한 스트리머 전부 재계산(전체 수정일때만)
	    if(streakChanged) {
	    	List<Integer> streamerNos = ckParticipantDao.selectStreamerNos(ckId);
	    	ckStreakService.refreshAll(streamerNos);
	    }
	    
	    return originDto;
	}

	@Transactional
	public void delete(int ckId, String bearerToken) {

	    CkDto originDto =
	            requireManagePermission(ckId, bearerToken);

	    // 삭제 전에 영향받는 스트리머 저장
	    List<Integer> streamerNos =
	            ckParticipantDao.selectStreamerNos(ckId);

	    // CK 삭제
	    ckDao.delete(originDto.getCkId());

	    if (!streamerNos.isEmpty()) {
	        // CK가 하나도 남지 않은 스트리머의 streak 삭제
	        ckStreakDao.deleteNoHistory(streamerNos);
	        // CK가 남은 스트리머는 MERGE 재계산
	        ckStreakService.refreshAll(streamerNos);
	    }
	}
	
	
}
