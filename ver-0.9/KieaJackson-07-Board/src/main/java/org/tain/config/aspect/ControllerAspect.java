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
public class ControllerAspect {

	@Before("execution(* org.tain.controller.*.*Aop(..))")
	public void onBeforeHandler(JoinPoint joinPoint) {
		log.info("KANG-20200618(AOP) >>>>> {}", CurrentInfo.get());
	}

	@After("execution(* org.tain.controller.*.*Aop(..))")
	public void onAfterHandler(JoinPoint joinPoint) {
		log.info("KANG-20200618(AOP) >>>>> {}", CurrentInfo.get());
	}

	@AfterReturning(pointcut = "execution(* org.tain.controller.*.*Aop(..))", returning = "obj")
	public void onAfterReturningHandler(JoinPoint joinPoint, Object obj) {
		String result = "";
		
		result += "\n*****\n";
		result += joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "(";
		
		int index = joinPoint.getArgs().length;
		for (Object object : joinPoint.getArgs()) {
			result += object;
			if (--index != 0) {
				result += ", ";
			}
		}
		
		result += ")\n";
		
		result += "Completed: " + joinPoint;
		result += "\n*****\n";
		
		log.info("KANG-20200618(AOP) >>>>> {}\n{}", CurrentInfo.get(), result);
	}

	@Pointcut("execution(* org.tain.controller.*.*Aop(..))")
	public void onPointcut(JoinPoint joinPoint) {
		log.info("KANG-20200618(AOP) >>>>> {}", CurrentInfo.get());
	}
}
