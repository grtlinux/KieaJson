package org.tain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tain.tasks.ProgrammableScheduler;

@Service
public class ProgrammableSchedulerService {

	@Autowired
	private ProgrammableScheduler scheduler;
	
	public void runScheduler() {
		// called by somewhere
		this.scheduler.startScheduler();
	}
}
