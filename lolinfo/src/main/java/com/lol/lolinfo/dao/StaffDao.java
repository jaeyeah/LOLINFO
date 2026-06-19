package com.lol.lolinfo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lol.lolinfo.dto.StaffDto;
import com.lol.lolinfo.vo.StaffListVO;
import com.lol.lolinfo.vo.TeamStaffVO;

@Repository
public class StaffDao {

	@Autowired
	private SqlSession sqlSession;
	
	//등록
	public void insert(StaffDto staffDto) {
		sqlSession.insert("staff.insert", staffDto);
	}
	//조회1
	public List<StaffListVO> selectListByStreamerNo(int streamerNo){
		return sqlSession.selectList("staff.selectListByStreamerNo", streamerNo);
	}
	//조회2 : 팀별 소속 감독/코치 조회
	public List<TeamStaffVO> selectStaffList(int teamId){
		return sqlSession.selectList("staff.selectStaffList", teamId);		
	}
	
	//삭제
	public void delete(StaffDto staffDto) {
		sqlSession.delete("staff.delete", staffDto);
	}
}
