package com.cryfirock.auth.service.aspects;

/**
 * Dependencies
 */
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

/**
 * Aspect for logging user service operations
 * Executes functions before, after and around service methods
 */
@Aspect
public class UserAspect {

    /**
     * Logger for tracking method executions
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Logs before execution of UserService methods
     * 
     * @param joinPoint provides access to method information
     */
    @Before("execution(* com.cryfirock.msvc.users.msvc_users.services.UserServiceImpl.*(..))")
    public void loggerBefore(JoinPoint joinPoint) {
        // Get method name and arguments
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Log before execution of method with parameters
        logger.info("Before executing: " + method + " with arguments " + args);
    }

    /**
     * Logs after execution of UserService methods (regardless of outcome)
     * 
     * @param joinPoint provides access to method information
     */
    @After("execution(* com.cryfirock.msvc.users.msvc_users.services.UserServiceImpl.*(..))")
    public void loggerAfter(JoinPoint joinPoint) {
        // Get method name and arguments
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Log after execution of method with parameters
        logger.info("After executing: " + method + " with arguments " + args);
    }

    /**
     * Logs after successful return from UserService methods
     * 
     * @param joinPoint provides access to method information
     */
    @AfterReturning("execution(* com.cryfirock.msvc.users.msvc_users.services.UserServiceImpl.*(..))")
    public void loggerAfterReturning(JoinPoint joinPoint) {
        // Get method name and arguments
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Log after successful execution of method with parameters
        logger.info("After successful execution: " + method + " with arguments " + args);
    }

    /**
     * Logs after exceptions in UserService methods
     * 
     * @param joinPoint provides access to method information
     */
    @AfterThrowing("execution(* com.cryfirock.msvc.users.msvc_users.services.UserServiceImpl.*(..))")
    public void loggerAfterThrowing(JoinPoint joinPoint) {
        // Get method name and arguments
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Log after exception in method with parameters
        logger.error("After exception in: " + method + " with arguments " + args);
    }

    /**
     * Wraps around UserService methods for comprehensive logging
     * 
     * @param joinPoint provides access to proceed with method execution
     * @return the method result
     * @throws Throwable if the method throws an exception
     */
    @Around("execution(* com.cryfirock.msvc.users.msvc_users.services.UserServiceImpl.*(..))")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get method name and arguments
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        // Log method entry with parameters
        Object result = null;

        // Execute the method and log the results and exceptions occurring
        try {
            logger.info("Entering method " + method + "() with parameters " + args);
            result = joinPoint.proceed();
            logger.info("Method " + method + "() returned: " + result);
            return result;
        } catch (Throwable e) {
            logger.error("Error in method " + method + "(): " + e.getMessage());
            throw e;
        }
    }
}