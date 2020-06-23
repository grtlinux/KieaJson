package org.tain.tasks;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		System.out.println("KANG-20200623 >>>>> " + LocalDateTime.now());
	}
}
