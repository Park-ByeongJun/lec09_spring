package com.gn.spring.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerService {
// 리턴은 항상 void , 매개변수 사용 불가
//	@Scheduled(cron = "0/1 * * * * ?")
//	public void checkTime() {
//		
//	}
	
}
