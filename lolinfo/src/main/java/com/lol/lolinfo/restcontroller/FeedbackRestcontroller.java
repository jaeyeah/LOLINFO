package com.lol.lolinfo.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.FeedbackDao;
import com.lol.lolinfo.dto.FeedbackDto;
import com.lol.lolinfo.vo.PageResponseVO;
import com.lol.lolinfo.vo.PageVO;

@CrossOrigin
@RestController
@RequestMapping("/api/feedback")
public class FeedbackRestcontroller {

	@Autowired
	private FeedbackDao feedbackDao;
	
	// 피드백 등록
    @PostMapping("/")
    public void insert(@RequestBody FeedbackDto feedbackDto) {
        feedbackDao.insert(feedbackDto);
		System.out.println("feedback 등록 실행");
	}
    
    // 목록조회 (페이지네이션)
    @GetMapping("/")
    public PageResponseVO<FeedbackDto> selectList(@RequestParam(defaultValue = "1") int page){
    	int totalCount = feedbackDao.count();
		PageVO pageVO = new PageVO();
		pageVO.setPage(page);
		pageVO.setTotalCount(totalCount);
		List<FeedbackDto> list = feedbackDao.selectList(pageVO);
		return new PageResponseVO<>(list, pageVO);
    }
	
	
}
