package org.tain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.tain.service.DynamicScheduleService;
import org.tain.service.ProgrammableSchedulerService;

@SpringBootApplication
@EnableScheduling
public class KieaJackson12Batch2Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson12Batch2Application.class, args);
	}
	
	///////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////
	
	@Autowired
	private ProgrammableSchedulerService programmableService;
	
	@Autowired
	private DynamicScheduleService dynamicService;
	
	@Override
	public void run(String... args) throws Exception {
		this.programmableService.runScheduler();
		this.dynamicService.startScheduler();
	}
}
