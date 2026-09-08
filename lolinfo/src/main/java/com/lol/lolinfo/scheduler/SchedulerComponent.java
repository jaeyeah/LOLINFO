package com.lol.lolinfo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lol.lolinfo.service.CkStreakService;

@Component
public class SchedulerComponent {

	@Autowired
	private CkStreakService ckStreakService;
	
	@Scheduled(cron = "0 0 11 * * *")
    public void rebuildCkStreak() {
        ckStreakService.rebuildAll();
    }
}
