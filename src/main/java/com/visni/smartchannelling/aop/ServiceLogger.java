package com.visni.smartchannelling.aop;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceLogger {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	//Before Advices runs before the method execution
	//* class name
	// * Method name
	
	@Before("execution(* com.visni.smartchannelling.controller.*.*(..))")
	private void logBeforeTheMethod(JoinPoint joinPoint){
		log.info("The method" +joinPoint.getSignature().getName()+"()begins with " + Arrays.toString(joinPoint.getArgs()));
	}
	
	//After Advices runs after a join point finishes, whenever it returns a result or throws an exception abnormally
	@After("execution(* com.visni.smartchannelling.controller.*.*(..))")
	private void logAfterTheMethod(JoinPoint joinPoint){
		log.info("The method"+joinPoint.getSignature().getName()+"() ends");
	}
	
	@AfterThrowing(pointcut="execution(* com.visni.smartchannelling.controller.*.*(..))", throwing = "e")
	private void logAfterThrowing(JoinPoint joinPoint, Throwable e	){
		log.error("An exception " + e + " has been thrown in "
				+ joinPoint.getSignature().getName() + "()");
		
	}
	

}
