package org.tain;

import org.springframework.util.StopWatch;

public class TestStopWatch {

	public static void main(String[] args) throws Exception {
		StopWatch stopWatch = new StopWatch("Stop Watch");
		stopWatch.start("initializing");
		Thread.sleep(2000);
		stopWatch.stop();
		System.out.println("took " + stopWatch.getLastTaskTimeMillis() + " ms");
		stopWatch.start("processing");
		Thread.sleep(5000);
		stopWatch.stop();
		System.out.println("took " + stopWatch.getLastTaskTimeMillis() + " ms");
		  
		stopWatch.start("finalizing");
		Thread.sleep(3000);
		stopWatch.stop();
		System.out.println("took " + stopWatch.getLastTaskTimeMillis() + " ms");
		  
		System.out.println(stopWatch.toString());
		System.out.println();
		System.out.println(stopWatch.prettyPrint());
	}
}
