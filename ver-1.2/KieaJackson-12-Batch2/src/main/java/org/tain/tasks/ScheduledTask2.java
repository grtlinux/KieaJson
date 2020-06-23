package org.tain.tasks;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask2 {

	//@Scheduled(cron = "${job.cron.rate}")
	public void simplePrintln() {
		System.out.println(">>>>> " + new Date());
	}
}
