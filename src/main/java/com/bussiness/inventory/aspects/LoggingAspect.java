package com.bussiness.inventory.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.bussiness.inventory.controller..*(..))")
    public void logBeforeController(JoinPoint joinPoint){
        logger.info("controller calling: {} with arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    
    @Before("execution(* com.bussiness.inventory.service..*(..))")
    public void logBeforeService(JoinPoint joinPoint){
        logger.info("service calling: {} with arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }

    @Around("execution(* com.bussiness.inventory.service..*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable{
       long startTime = System.currentTimeMillis();

       Object result = joinPoint.proceed();
       long endTime = System.currentTimeMillis();
       long executionTime = endTime - startTime;
       logger.info("{} method executed in {} ms", joinPoint.getSignature().getName(), executionTime);
       return result;
    }

    @AfterThrowing(pointcut="execution(* com.bussiness.inventory..*(..))", throwing="exception")
    public void logException(JoinPoint joinpoint, Throwable exception){
        logger.error("[Error] Exception in {}:{}", joinpoint.getSignature().getName(), exception.getMessage());
    }

    @AfterReturning(pointcut = "execution(* com.bussiness.inventory.service..*(..))", returning="result")
    public void logAfterReturning(JoinPoint joinoint, Object result){
        logger.info("{} completed successfully", joinoint.getSignature().getName());
    }

}
