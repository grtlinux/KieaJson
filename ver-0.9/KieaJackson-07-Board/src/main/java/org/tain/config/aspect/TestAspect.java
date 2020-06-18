package org.tain.config.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.tain.utils.Flag;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class TestAspect {

	@Around("execution(* org.tain.controller.TestController.*(..))")
	public Object loggingTest(ProceedingJoinPoint pjp) throws Throwable {
		Object result = null;

		StopWatch stopWatch = new StopWatch("Hello, world!!!");
		stopWatch.start("testing 01");
		if (Flag.flag) {
			log.info("KANG-20200618(AOP) >>>>> start: {}", pjp.getSignature().getDeclaringTypeName() + "/" + pjp.getSignature().getName());
			result = pjp.proceed();
			log.info("KANG-20200618(AOP) >>>>> finished: {}", pjp.getSignature().getDeclaringTypeName() + "/" + pjp.getSignature().getName());
		}
		stopWatch.stop();
		if (Flag.flag) System.out.println(">> stopWatch.getLastTaskTimeMillis(): " + stopWatch.getLastTaskTimeMillis());
		if (Flag.flag) System.out.println(">> stopWatch.toString(): " + stopWatch.toString());
		if (Flag.flag) System.out.println(">> stopWatch.prettyPrint(): " + stopWatch.prettyPrint());

		if (Flag.flag) {
			String className = pjp.getTarget().getClass().getName();
			String methodName = pjp.getSignature().getName();
			String taskName = className + ":" + methodName + "(..)";
			if (Flag.flag) System.out.println(">> " + taskName);
		}
		
		return result;
	}
}
