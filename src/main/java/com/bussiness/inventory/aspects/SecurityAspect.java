package com.bussiness.inventory.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class SecurityAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Before("execution(* com.bussiness.inventory.service.UserService.signup(..))")
    public void logSecurityOperation(JoinPoint joinpoint){
        logger.warn("user signup attempt - Method: {}", joinpoint.getSignature().getName());
    }
}
