package org.tain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tain.service.SchedulerService;

@SpringBootApplication
public class KieaJackson11BatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson11BatchApplication.class, args);
	}
	
	/////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////

	@Autowired
	private SchedulerService schedulerService;
	
	@Override
	public void run(String... args) throws Exception {
		this.schedulerService.start();
	}
}
