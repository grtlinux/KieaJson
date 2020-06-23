package org.tain.tasks;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

@Component
public class ProgrammableScheduler {

	private ThreadPoolTaskScheduler scheduler;
	
	public void stopScheduler() {
		this.scheduler.shutdown();
	}
	
	public void startScheduler() {
		this.scheduler = new ThreadPoolTaskScheduler();
		this.scheduler.initialize();
		this.scheduler.schedule(this.getRunnable(), this.getTrigger());
	}
	
	private Runnable getRunnable() {
		return () -> {
			// do something
			System.out.println(">>>>> " + new Date());
		};
	}
	
	private Trigger getTrigger() {
		// Job period
		Random random = new Random(new Date().getTime());
		
		if (random.nextInt() % 2 == 0) {
			return new PeriodicTrigger(1, TimeUnit.SECONDS);
		} else {
			return new CronTrigger("*/3 * * * * *");
		}
	}
}
