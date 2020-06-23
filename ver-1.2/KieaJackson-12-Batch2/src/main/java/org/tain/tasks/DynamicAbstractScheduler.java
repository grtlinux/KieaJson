package org.tain.tasks;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public abstract class DynamicAbstractScheduler {

	private ThreadPoolTaskScheduler scheduler;
	
	public void stopScheduler() {
		this.scheduler.shutdown();
	}
	
	public void startScheduler() {
		this.scheduler = new ThreadPoolTaskScheduler();
		this.scheduler.initialize();
		this.scheduler.schedule(getRunnable(), getTrigger());
	}
	
	private Runnable getRunnable() {
		return new Runnable() {
			@Override
			public void run() {
				runner();
			}
		};
	}
	
	public abstract void runner();
	
	public abstract Trigger getTrigger();
}
