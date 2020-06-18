package org.tain.config.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.tain.utils.CurrentInfo;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ServiceAspect {

	@Before("execution(* org.tain.service.*.*Aop(..))")
	public void onBeforeHandler(JoinPoint joinPoint) {
		log.info("KANG-20200618(AOP) >>>>> {}", CurrentInfo.get());
	}

	@After("execution(* org.tain.service.*.*Aop(..))")
	public void onAfterHandler(JoinPoint joinPoint) {
		log.info("KANG-20200618(AOP) >>>>> {}", CurrentInfo.get());
	}

	@AfterReturning(pointcut = "execution(* org.tain.service.*.*Aop(..))", returning = "obj")
	public void onAfterReturningHandler(JoinPoint joinPoint, Object obj) {
		log.info("KANG-20200618(AOP) >>>>> {} RETURN:{}", CurrentInfo.get(), obj);
	}

	@Pointcut("execution(* org.tain.service.*.*Aop(..))")
	public void onPointcut(JoinPoint joinPoint) {
		log.info("KANG-20200618(AOP) >>>>> {}", CurrentInfo.get());
	}
}
