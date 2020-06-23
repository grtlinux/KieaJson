package org.tain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tain.service.SchedulerService;

@SpringBootTest
public class SchedulerServiceTest {

	@Autowired
	private SchedulerService schedulerService;
	
	@Test
	public void testSchedulerService1() {
		this.schedulerService.start();
		try { Thread.sleep(20 * 1000); } catch (InterruptedException e) {}
	}
	
	@Test
	public void testSchedulerService2() {
		this.schedulerService.start();
		try { Thread.sleep(10 * 1000); } catch (InterruptedException e) {}
		
		String cron = "*/5 * * * * *";
		this.schedulerService.changeCron(cron);
		try { Thread.sleep(20 * 1000); } catch (InterruptedException e) {}
	}
}
