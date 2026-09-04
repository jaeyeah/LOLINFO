package com.lol.lolinfo.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lol.lolinfo.dao.FeedbackDao;
import com.lol.lolinfo.dto.FeedbackDto;

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
	
	
}
