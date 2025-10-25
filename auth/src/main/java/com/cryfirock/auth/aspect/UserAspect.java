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

/**
 * ==================================================================================================
 * Paso 12.1: Aspecto para registrar las operaciones del servicio de usuarios
 * ==================================================================================================
 */
@Aspect
@Component
public class UserAspect {

    /**
     * ==============================================================================================
     * Paso 12.2: Atributos
     * ==============================================================================================
     */
    // Registro para rastrear la ejecución de métodos
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * ==============================================================================================
     * Paso 12.3: Advice before
     * ==============================================================================================
     */
    @Before("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public void loggerBefore(JoinPoint joinPoint) {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        // Registra antes de ejecutar el método con sus parámetros
        logger.info("Antes de ejecutar: " + method + " con argumentos " + args);
    }

    /**
     * ==============================================================================================
     * Paso 12.4: Advice after
     * ==============================================================================================
     */
    @After("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public void loggerAfter(JoinPoint joinPoint) {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        // Registra después de ejecutar el método con sus parámetros
        logger.info("Después de ejecutar: " + method + " con argumentos " + args);
    }

    /**
     * ==============================================================================================
     * Paso 12.5: Advice after returning
     * ==============================================================================================
     */
    @AfterReturning("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public void loggerAfterReturning(JoinPoint joinPoint) {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        // Registra tras la ejecución exitosa del método con sus parámetros
        logger.info("Después de una ejecución exitosa: " + method + " con argumentos " + args);
    }

    /**
     * ==============================================================================================
     * Paso 12.6: Advice after throwing
     * ==============================================================================================
     */
    @AfterThrowing("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public void loggerAfterThrowing(JoinPoint joinPoint) {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        // Registra después de la excepción en el método con sus parámetros
        logger.error("Después de una excepción en: " + method + " con argumentos " + args);
    }

    /**
     * ==============================================================================================
     * Paso 12.7: Advice around
     * ==============================================================================================
     */
    @Around("execution(* com.cryfirock.auth.service.UserServiceImpl.*(..))")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // Obtiene el nombre del método y los argumentos
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());
        // Ejecuta el método y registra los resultados y posibles excepciones
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
