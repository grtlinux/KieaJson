package org.tain.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {

	@Autowired
	private TaskScheduler taskScheduler;
	
	private String cron = "*/3 * * * * *";
	private ScheduledFuture<?> future;
	
	public void start() {
		this.future = this.taskScheduler.schedule(() -> {
			// cron job
			System.out.println("KANG-20200623 >>>>> run at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		}, new CronTrigger(this.cron));
	}
	
	public void changeCron(String cron) {
		this.future.cancel(true);
		this.future = null;
		this.cron = cron;
		this.start();
	}
}
