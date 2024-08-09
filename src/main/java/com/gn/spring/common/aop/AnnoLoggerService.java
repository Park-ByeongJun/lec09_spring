package com.gn.spring.common.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AnnoLoggerService {
	
	Logger LOGGER = LogManager.getLogger(LoggerService.class);
	
	@Pointcut("execution(* com.gn.spring..*(..))")
	private void boardLogger() {}
	
	// 특정메소드 실행 전에 동작할 코드
//	@Before("boardLogger()")
//	public void loggerBefore(JoinPoint jp) {
//		String className = jp.getTarget().getClass().getName();
//		String methodName = jp.getSignature().getName();
//		LOGGER.info(className+"."+methodName+"()실행전");
//	}
	
	// 특정 메소드 실행 후에 동작할 메소드
	@After("boardLogger()")
	public void loggerAfter(JoinPoint jp) {
		String className = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		LOGGER.info(className+"."+methodName+"()실행후");
	}
}
