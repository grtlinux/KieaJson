package org.tain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KieaJackson12Batch2Application {

	public static void main(String[] args) {
		SpringApplication.run(KieaJackson12Batch2Application.class, args);
	}
}
