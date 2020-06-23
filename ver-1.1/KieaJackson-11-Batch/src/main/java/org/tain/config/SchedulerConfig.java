package org.tain.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class SchedulerConfig {

	@Bean
	public ThreadPoolTaskScheduler schedulerExecutor() {
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(4);
		taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		return taskScheduler;
	}
}
