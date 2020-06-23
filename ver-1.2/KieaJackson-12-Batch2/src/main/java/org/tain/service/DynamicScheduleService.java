package org.tain.service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Service;
import org.tain.tasks.DynamicAbstractScheduler;

@Service
public class DynamicScheduleService extends DynamicAbstractScheduler {

	@Override
	public void runner() {
		System.out.println("KANG-20200623 >>>>> " + LocalDateTime.now());
	}
	
	@Override
	public Trigger getTrigger() {
		return new PeriodicTrigger(7, TimeUnit.SECONDS);
	}
}
