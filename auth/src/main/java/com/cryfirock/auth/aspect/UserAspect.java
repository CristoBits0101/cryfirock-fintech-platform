package com.cryfirock.auth.aspect;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class UserAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Before("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public void loggerBefore(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Antes de ejecutar: " + method + " con argumentos " + args);
    }
    @After("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public void loggerAfter(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Después de ejecutar: " + method + " con argumentos " + args);
    }
    @AfterReturning("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public void loggerAfterReturning(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.info("Después de una ejecución exitosa: " + method + " con argumentos " + args);
    }
    @AfterThrowing("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public void loggerAfterThrowing(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        logger.error("Después de una excepción en: " + method + " con argumentos " + args);
    }
    @Around("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        try {
            logger.info("Entrando al método " + method + "() con parámetros " + args);
            Object result = joinPoint.proceed();
            logger.info("El método " + method + "() devolvió: " + result);
            return result;
        } catch (Throwable e) {
            logger.error("Error en el método " + method + "(): " + e.getMessage());
            throw e;
        }
    }
}
